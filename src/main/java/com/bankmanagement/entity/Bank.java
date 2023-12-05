package com.bankmanagement.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    @Id
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
