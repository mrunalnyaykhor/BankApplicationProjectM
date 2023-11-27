package com.bankmanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    @Column(name = "accountNumberFrom")
    private Long accountNumberFrom;
    @Column(name = "accountNumberTo")
    private Long accountNumberTo;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "transactionDate")
    private LocalDate transactionDate;
    @Column(name = "description")
    private String description;
    @Column(name = "ifscCode")
    private  String ifscCode;
    @Column(name="name")
    private String name;




}
