package ru.vldaislab.bekrenev.bankcardsmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.Card;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.transaction.Transaction;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Arrays findTransactionByCard_Id(Long cardId);

    List<Transaction> findTransactionByCard(Card card);
}
