package com.bankmanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bankId;
    @Column(name = "bankName", nullable = false, length = 20)
    private String bankName;
    @Column(name = "branchName", nullable = false, length = 20)
    private String branchName;
    @Column(name = "ifscCode", nullable = false)
    private String ifscCode;
    @Column(name = "address", nullable = false, length = 20)
    private String address;

}
