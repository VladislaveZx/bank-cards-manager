package ru.vldaislab.bekrenev.bankcardsmanager.entity.card;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.user.User;

import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Номер карты (masked, хранится в зашифрованном виде)
     */
    @Column(name = "card_number", length = 16, nullable = false, unique = true)
    private String cardNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User owner;

    /**
     * Срок действия
     */
    @Column(name = "expiry_date", nullable = false)
    private YearMonth expiryDate;


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

}
