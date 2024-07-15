package com.mega.project.utm.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MapAmlaSummary {

    @JsonProperty("sum")
    private Long sum;
    @JsonProperty("count")
    private Long count;
    @JsonProperty("olst_txn_code")
    private Integer olstTxnCode;
    @JsonProperty("julian_mis_date")
    private Integer julianMisDate;
    @JsonProperty("olst_txn_bank_nbr")
    private Integer olstTxnBankNumber;
    @JsonProperty("olst_txn_desc")
    private String olstTxnDesc;
}
