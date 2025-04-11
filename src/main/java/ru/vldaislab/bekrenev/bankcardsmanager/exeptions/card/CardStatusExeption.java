package ru.vldaislab.bekrenev.bankcardsmanager.exeptions.card;

public class CardStatusExeption extends RuntimeException {
    public CardStatusExeption(String message) {
        super(message);
    }
}
