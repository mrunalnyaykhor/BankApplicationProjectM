package com.bankmanagement.entity;

import com.bankmanagement.enump.AccountType;
import lombok.*;


import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;


@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    @Column(name = "amount")
    private double amount;
    @Column(name = "blocked")
    private boolean isBlocked = false;
    @Column(name = "accountNumber")
    private long accountNumber;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bankId", referencedColumnName = "bankId")
    private Bank bank;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    private Customer customer;
    @Column(insertable = false, updatable = false)
    private Long customerId;
    @Column(insertable = false, updatable = false)
    private Long bankId;
    @Column(name = "accountType")
    private AccountType accountType;
}



