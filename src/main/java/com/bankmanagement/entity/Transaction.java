package com.bankmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    @Column(name = "accountNumberFrom", nullable = false)
    private Long accountNumberFrom;
    @Column(name = "accountNumberTo", nullable = false)
    private Long accountNumberTo;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "transactionDate", nullable = false)
    private LocalDate transactionDate;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "ifscCode", nullable = false)

    private  String ifscCode;




}
