package ru.vldaislab.bekrenev.bankcardsmanager.services.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.Card;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.transaction.Transaction;
import ru.vldaislab.bekrenev.bankcardsmanager.exeptions.card.CardNotFoundExeption;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.CardRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.TransactionRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.UserRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.services.card.CardService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardService cardService;
    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByCardId(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()->new CardNotFoundExeption("Карта с таким номером не найдена"));
        return transactionRepository.findTransactionByCard(card);
    }

    public void makeTransaction(BigDecimal amount, Long cardIdFrom, Long cardIdTo) {
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(amount);
        transaction.setTransactionDateTime(LocalDateTime.now());
        transaction.setCard(cardRepository.getCardsById(cardIdFrom));

        Card cardFrom = cardRepository.getCardsById(cardIdFrom);
        cardFrom.setBalance(cardFrom.getBalance().subtract(amount));
        Card cardTo = cardRepository.getCardsById(cardIdTo);
        cardTo.setBalance(cardTo.getBalance().add(amount));

        cardRepository.save(cardFrom);
        cardRepository.save(cardTo);

        transactionRepository.save(transaction);
    }

    public void makeTransactionTo(BigDecimal amount, Long cardIdFrom) {
    }
    public void makeTransactionFrom(BigDecimal amount, Long cardIdTo) {

    }
}
