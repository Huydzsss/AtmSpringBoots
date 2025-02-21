package org.example.atm.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime timeTrans = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}