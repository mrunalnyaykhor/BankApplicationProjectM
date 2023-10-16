package com.bankmanagement.entity;

import lombok.AllArgsConstructor;
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
    @Column(name = "bankName")
    private String bankName;
    @Column(name = "branchName")
    private String branchName;
    @Column(name = "ifscCode")
    private String ifscCode;
    @Column(name = "address")
    private String address;

}
