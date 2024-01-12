package com.bankmanagement.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;


@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "bankId")
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
