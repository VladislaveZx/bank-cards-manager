package ru.vldaislab.bekrenev.bankcardsmanager.exeptions.card;

import ru.vldaislab.bekrenev.bankcardsmanager.entity.user.User;

public class CardNotFoundExeption extends RuntimeException {
    public CardNotFoundExeption(String message) {
        super(message);
    }
}
