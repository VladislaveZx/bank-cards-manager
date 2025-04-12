package ru.vldaislab.bekrenev.bankcardsmanager.transaction;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.Card;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.CardStatus;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.transaction.Transaction;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.CardRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.TransactionRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.services.transaction.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class TransactionTeast {
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final CardRepository cardRepository;
    // Handle case when card ID doesn't exist when retrieving transactions
    @Test
    public void test_get_transactions_by_card_id_not_found() {
        // Arrange
        Long nonExistentCardId = 999L;

        when(transactionRepository.getTransactionByCardId(nonExistentCardId)).thenReturn(Collections.emptyList());

        // Act
        List<Transaction> transactions = transactionService.getTransactionsByCardId(nonExistentCardId);

        // Assert
        assertTrue(transactions.isEmpty());
        verify(transactionRepository).getTransactionByCardId(nonExistentCardId);
    }

    // Successfully retrieve transactions for a valid card ID
    @Test
    public void test_get_transactions_by_card_id_success() {
        // Arrange
        Long cardId = 1L;
        List<Transaction> expectedTransactions = Arrays.asList(
                new Transaction(UUID.randomUUID(), new BigDecimal("100.00"), LocalDateTime.now(), new Card(), new Card()),
                new Transaction(UUID.randomUUID(), new BigDecimal("200.00"), LocalDateTime.now(), new Card(), new Card())
        );

        when(transactionRepository.getTransactionByCardId(cardId)).thenReturn(expectedTransactions);

        // Act
        List<Transaction> actualTransactions = transactionService.getTransactionsByCardId(cardId);

        // Assert
        assertEquals(expectedTransactions, actualTransactions);
        verify(transactionRepository).getTransactionByCardId(cardId);
    }
    @Test
    public void test_successful_transaction_between_two_valid_cards() {
        Card cardFrom = new Card(1L, "1234567890123456", null, YearMonth.now(), CardStatus.ACTIVE, BigDecimal.valueOf(1000));
        Card cardTo = new Card(2L, "6543210987654321", null, YearMonth.now(), CardStatus.ACTIVE, BigDecimal.valueOf(500));

        when(cardRepository.getCardsById(1L)).thenReturn(cardFrom);
        when(cardRepository.getCardsById(2L)).thenReturn(cardTo);

        transactionService.makeTransaction(BigDecimal.valueOf(200), 1L, 2L);

        assertEquals(BigDecimal.valueOf(800), cardFrom.getBalance());
        assertEquals(BigDecimal.valueOf(700), cardTo.getBalance());

        verify(cardRepository).save(cardFrom);
        verify(cardRepository).save(cardTo);
    }

    @Test
    public void test_transaction_saved_with_correct_details() {
        Card cardFrom = new Card(1L, "1234567890123456", null, YearMonth.now(), CardStatus.ACTIVE, BigDecimal.valueOf(1000));
        Card cardTo = new Card(2L, "6543210987654321", null, YearMonth.now(), CardStatus.ACTIVE, BigDecimal.valueOf(500));

        when(cardRepository.getCardsById(1L)).thenReturn(cardFrom);
        when(cardRepository.getCardsById(2L)).thenReturn(cardTo);

        transactionService.makeTransaction(BigDecimal.valueOf(200), 1L, 2L);

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionCaptor.capture());

        Transaction savedTransaction = transactionCaptor.getValue();
        assertEquals(BigDecimal.valueOf(200), savedTransaction.getTransactionAmount());
        assertNotNull(savedTransaction.getTransactionDateTime());
        assertEquals(cardFrom, savedTransaction.getCardFrom());
        assertEquals(cardTo, savedTransaction.getCardTo());
    }
}
