package ru.vldaislab.bekrenev.bankcardsmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
