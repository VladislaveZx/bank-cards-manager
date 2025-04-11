package ru.vldaislab.bekrenev.bankcardsmanager.services.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.Card;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.CardStatus;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.user.User;
import ru.vldaislab.bekrenev.bankcardsmanager.exeptions.card.CardStatusExeption;
import ru.vldaislab.bekrenev.bankcardsmanager.exeptions.user.UserNotFoundException;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.CardRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.TransactionRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;


    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    @Transactional
    public Card createCard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        Card card = new Card();
        card.setOwner(user);
        card.setExpiryDate(YearMonth.now().plusYears(5));
        card.setCardStatus(CardStatus.ACTIVE); // По умолчанию карта активна
        card.setBalance(BigDecimal.ZERO); // Начальный баланс

        return cardRepository.save(card);
    }

    @Transactional
    public void changeCardStatusOnActive(Long cardId) {
        if (cardRepository.getCardsById(cardId).getCardStatus() == CardStatus.ACTIVE) {
            throw  new CardStatusExeption("Карта уже заблокирована");
        }
        Card card = cardRepository.getCardsById(cardId);
        card.setCardStatus(CardStatus.ACTIVE);
        cardRepository.save(card);
    }

    @Transactional
    public void changeCardStatusOnBlocked(Long cardId) {
        if (cardRepository.getCardsById(cardId).getCardStatus() == CardStatus.BLOCKED) {
            throw  new CardStatusExeption("Карта уже заблокирована");
        }
        Card card = cardRepository.getCardsById(cardId);
        card.setCardStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
    }

    @Transactional
    public void changeCardStatusOnExpired(Long cardId) {
        if (YearMonth.now().equals(cardRepository.getCardsById(cardId).getExpiryDate())) {
            Card card = cardRepository.getCardsById(cardId);
            card.setCardStatus(CardStatus.EXPIRED);
            cardRepository.save(card);
        }
        else throw new CardStatusExeption("Срок действия карты еще не истек, карта активна до: "
                + cardRepository.getCardsById(cardId).getExpiryDate());
    }

    @Transactional
    public void deleteCard(Long cardId){
        if (cardRepository.getCardsById(cardId).getCardStatus() == CardStatus.BLOCKED) {
            throw  new CardStatusExeption("Такой карты не существует");
        }
        Card card = cardRepository.getCardsById(cardId);
        cardRepository.delete(card);
    }

}
