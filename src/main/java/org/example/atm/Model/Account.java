package org.example.atm.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accountNumber", nullable = false,unique = true, length = 255)
    private String accountNumber;
    @Column(name = "pin", nullable = false, length = 6)
    private String pin;
    @Column(name = "nameAccount", nullable = false, length = 255)
    private String nameAccount;

    @Column(name = "balance", nullable = false)
    private double balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Transaction> transactions;
}
