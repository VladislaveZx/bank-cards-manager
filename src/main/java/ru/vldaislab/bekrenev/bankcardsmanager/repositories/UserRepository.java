package ru.vldaislab.bekrenev.bankcardsmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
