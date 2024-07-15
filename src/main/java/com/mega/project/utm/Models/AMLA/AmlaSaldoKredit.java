package com.mega.project.utm.Models.AMLA;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SALDO_KREDIT")
public class AmlaSaldoKredit {

    @Id
    @Column(unique = true)
    @GeneratedValue(generator = "coba")
    @GenericGenerator(name = "coba", strategy = "com.mega.project.utm.services.StringUUIDGenerator")
    private String id;

    @Column(name = "julian_mis_date")
    private Long julianDate;

    @Column(name = "mis_date")
    private String misDate;

    @Column(name = "bank_number")
    private Long bankNumber;

    private String remarks;

    private String kredit;

    @Column(name = "nomor_kartu_kredit")
    private String ccNumber;

    @Column(name = "nama_pemilik_cc")
    private String custLocalName;

    @Column(name = "fin_acct")
    private String accNumber;

    @Column(name = "card_block")
    private String cardBlock;

    @Column(name = "card_status")
    private String cardStatus;

    @Column(name = "date_block")
    private Long dateBlock;

    @Column(name = "account_block")
    private String accountBlockCode;

    @Column(name = "last_stmt_date")
    private Long lastStmDate;

    @Column(name = "due_date")
    private Long dueDate;

    @Column(name = "limit")
    private String limit;

    @Column(name = "os")
    private String os;

    @Column(name = "trx_gantung")
    private String trxGantung;

    @Column(name = "company_city")
    private String companyCity;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "apu_ppt")
    private String custGoccCode;

    private String triggeredRule;

    private LocalDate createdAt;

}
