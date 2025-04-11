package ru.vldaislab.bekrenev.bankcardsmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.Card;


import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card getCardsById(Long cardId);

    boolean existsByCardNumber(String cardNumber);
}
