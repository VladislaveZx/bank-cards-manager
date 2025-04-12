package ru.vldaislab.bekrenev.bankcardsmanager.services.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByCardId(Long cardId) throws CardNotFoundExeption {
        return transactionRepository.getTransactionByCardId(cardId);
    }

    @Transactional
    public void makeTransaction(BigDecimal amount, Long cardIdFrom, Long cardIdTo) {
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(amount);
        transaction.setTransactionDateTime(LocalDateTime.now());
        transaction.setCardFrom(cardRepository.getCardsById(cardIdFrom));
        transaction.setCardTo(cardRepository.getCardsById(cardIdTo));

        Card cardFrom = cardRepository.getCardsById(cardIdFrom);
        cardFrom.setBalance(cardFrom.getBalance().subtract(amount));
        Card cardTo = cardRepository.getCardsById(cardIdTo);
        cardTo.setBalance(cardTo.getBalance().add(amount));

        cardRepository.save(cardFrom);
        cardRepository.save(cardTo);

        transactionRepository.save(transaction);
    }
}
