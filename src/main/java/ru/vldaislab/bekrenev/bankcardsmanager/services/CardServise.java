package ru.vldaislab.bekrenev.bankcardsmanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.Card;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.CardStatus;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.user.User;
import ru.vldaislab.bekrenev.bankcardsmanager.exeptions.user.UserNotFoundException;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.CardRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.UserRepository;

import java.time.YearMonth;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServise {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Card createCard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        Card card = new Card();
        card.setOwner(user);
        card.setExpiryDate(YearMonth.now().plusYears(5));
        card.setCardStatus(CardStatus.ACTIVE); // По умолчанию карта активна
        card.setBalance(java.math.BigDecimal.ZERO); // Начальный баланс

        return cardRepository.save(card);
    }

    public void chngeCardStatusOnActive(UUID cardId) {
        Card card = cardRepository.getCardsById(cardId);
        card.setCardStatus(CardStatus.ACTIVE);
    }

    public void chngeCardStatusOnBlocked(UUID cardId) {
        Card card = cardRepository.getCardsById(cardId);
        card.setCardStatus(CardStatus.BLOCKED);
    }

    public void chngeCardStatusOnExpired(UUID cardId) {
        Card card = cardRepository.getCardsById(cardId);
        card.setCardStatus(CardStatus.EXPIRED);
    }


}
