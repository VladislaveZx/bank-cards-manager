package ru.vldaislab.bekrenev.bankcardsmanager.entity.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vldaislab.bekrenev.bankcardsmanager.entity.card.Card;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "transaction_amount", nullable = false)
    private BigDecimal transactionAmount;

    @Column(name = "transaction_date_time", nullable = false)
    private LocalDateTime transactionDateTime;

//    @ManyToMany
//    @JoinTable(
//            name = "transactions_cards",
//            joinColumns = @JoinColumn(name = "transaction_id"),
//            inverseJoinColumns = @JoinColumn(name = "card_id")
//    )
    @ManyToOne
    @JoinColumn(name = "card_id_from", referencedColumnName = "id")
    private Card cardFrom;

    @ManyToOne
    @JoinColumn(name = "card_id_to", referencedColumnName = "id")
    private Card cardTo;
}
