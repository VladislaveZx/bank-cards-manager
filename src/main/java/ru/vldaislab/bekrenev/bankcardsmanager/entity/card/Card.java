package ru.vldaislab.bekrenev.bankcardsmanager.entity.card;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.transaction.Transaction;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.user.User;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card {

    /**
     * Номер карты (masked, хранится в зашифрованном виде)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

//    @Column(name = "encrypted_card_number", nullable = false)
//    private String encryptedCardNumber;

    /**
     * Владелец карты
     */
    @Column(name = "owner", nullable = false)
    @ManyToOne
    private User owner;


    /**
     * Срок действия
     */
    @Column(name = "expiry_date", nullable = false)
    private YearMonth yearMonth;


    /**
     * Статус карты (Активна, Заблокирована, Истек срок действия)
     */
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;


    /**
     * Баланс
     */
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany
    private Transaction transaction;
    //TODO: добавить историю транзакций (связь с Transaction)
}
