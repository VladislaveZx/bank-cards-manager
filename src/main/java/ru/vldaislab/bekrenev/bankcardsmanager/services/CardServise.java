package ru.vldaislab.bekrenev.bankcardsmanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.Card;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.CardRepository;

@Service
@RequiredArgsConstructor
public class CardServise {

    private final CardRepository cardRepository;

    public Card makeNewCard(String cardNumber) {

    }

}
