package ru.vldaislab.bekrenev.bankcardsmanager.exeptions.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
