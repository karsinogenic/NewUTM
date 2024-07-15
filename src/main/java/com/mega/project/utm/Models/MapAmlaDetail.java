package com.mega.project.utm.Models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MapAmlaDetail {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("julian_mis_date")
    private Integer julianMisDate;

    @JsonProperty("olst_txn_bank_nbr")
    private Integer olstTxnBankNumber;

    @JsonProperty("olst_txn_code")
    private Integer olstTxnCode;

    @JsonProperty("olst_txn_amt")
    private Integer olstTxnAmt;

    @JsonProperty("olst_txn_desc")
    private String olstTxnDesc;

    @JsonProperty("olst_txn_posting_dte")
    private Integer olstTxnPostingDate;

    @JsonProperty("olst_mcc_code")
    private Integer olstMccCode;

    @JsonProperty("olst_txn_acct_nbr")
    private String olstTxnAcctNumber;

    @JsonProperty("olst_txn_card_nbr")
    private String olstTxnCardNumber;

    @JsonProperty("olst_pos_entry_mode")
    private Integer olstPosEntryMode;

    @JsonProperty("olst_mech_nbr")
    private Long olstMechNumber;

    @JsonProperty("create_date")
    private String createDate;

}
