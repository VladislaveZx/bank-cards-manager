package ru.vldaislab.bekrenev.bankcardsmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
