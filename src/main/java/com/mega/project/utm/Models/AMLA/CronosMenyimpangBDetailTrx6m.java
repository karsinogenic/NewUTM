package com.mega.project.utm.Models.AMLA;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
// @Entity
// @Table(name = "cronos_menyimpang_b_detail_trx_6m")
public class CronosMenyimpangBDetailTrx6m {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "julian_mis_date")
    private Integer julianMisDate;

    @Column(name = "olst_txn_bank_nbr")
    private Integer olstTxnBankNumber;

    @Column(name = "olst_txn_code")
    private Integer olstTxnCode;

    @Column(name = "olst_txn_desc")
    private String olstTxnDesc;

    @Column(name = "olst_txn_posting_dte")
    private Integer olstTxnPostingDate;

    @Column(name = "olst_mcc_code")
    private Integer olstMccCode;

    @Column(name = "olst_txn_acct_nbr")
    private String olstTxnAcctNumber;

    @Column(name = "olst_txn_card_nbr")
    private String olstTxnCardNumber;

    @Column(name = "olst_pos_entry_mode")
    private Integer olstPosEntryMode;

    @Column(name = "olst_mech_nbr")
    private Long olstMechNumber;

    @Column(name = "create_date")
    private LocalDateTime createDate;

}
