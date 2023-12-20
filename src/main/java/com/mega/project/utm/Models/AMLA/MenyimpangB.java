package com.mega.project.utm.Models.AMLA;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "MENYIMPANG_B")
public class MenyimpangB {

    @Id
    @Column(unique = true)
    @GeneratedValue(generator = "coba")
    @GenericGenerator(name = "coba", strategy = "com.mega.project.utm.services.StringUUIDGenerator")
    private String id;

    @Column(name = "julian_mis_date")
    private Long julianDate;

    @Column(name = "txn_bank_nbr")
    private Long bankNumber;

    @Column(name = "txn_acct_nbr")
    private String accNumber;

    @Column(name = "cust_local_name")
    private String custLocalName;

    @Column(name = "cust_emp_name")
    private String custEmpName;

    @Column(name = "tot_trx")
    private Double totalTrx;

    @Column(name = "crdacct_blk_code")
    private String accountBlockCode;

    @Column(name = "crdacct_perm_crlimit")
    private Double accountPermCrLimit;

    @Column(name = "max_limit")
    private Double maxLimit;

    @Column(name = "cust_gocc_code")
    private String custGoccCode;

    @Column(name = "old_stm_date")
    private Long oldStmDate;

    private String triggeredRule;

    private LocalDate createdAt;

}
