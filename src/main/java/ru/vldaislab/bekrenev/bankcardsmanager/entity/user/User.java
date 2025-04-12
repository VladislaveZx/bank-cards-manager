package ru.vldaislab.bekrenev.bankcardsmanager.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = true)
    private String name;

    @Column(name = "email",nullable = true, unique = true)
    private String email;

    @Column(name = "password",nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}
