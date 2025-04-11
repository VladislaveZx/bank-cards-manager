package ru.vldaislab.bekrenev.bankcardsmanager.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.user.User;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.CardRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.TransactionRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.repositories.UserRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.services.card.CardService;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CardService cardService;

    public void createUser(User user) {
        userRepository.save(user);
    }

}
