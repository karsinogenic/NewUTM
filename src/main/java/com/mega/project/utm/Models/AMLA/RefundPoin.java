package com.mega.project.utm.Models.AMLA;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "REFUND_POIN")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class RefundPoin {

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

    @Column(name = "txn_card_nbr")
    private String cardNumber;

    @Column(name = "cust_local_name")
    private String custLocalName;

    @Column(name = "cust_gocc_code")
    private String custGoccCode;

    @Column(name = "cust_emp_name")
    private String custEmpName;

    @Column(name = "crdacct_blk_code")
    private String accountBlockCode;

    @Column(name = "card_blk_code")
    private String cardBlockCode;

    @Column(name = "card_status")
    private String cardStatus;

    @Column(name = "txn_date")
    private Long txnDate;

    @Column(name = "txn_posting_dte")
    private Long txnPostingDate;

    @Column(name = "txn_mech_nbr")
    private Long txnMechNumber;

    @Column(name = "txn_desc")
    private String txnDesc;

    @Column(name = "txn_mcc_code")
    private Long txnMccCode;

    @Column(name = "txn_code")
    private Long txnCode;

    @Column(name = "txn_auth_code")
    private String txnAuthCode;

    @Column(name = "txn_amt")
    private Double txnAmount;

    @Column(name = "crdacct_perm_crlimit")
    private Double accountPermCrLimit;

    @Column(name = "double_limit")
    private Double doubleLimit;

    @Column(name = "triggered_rule")
    private String triggeredRule;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public String getId() {
        return id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getJulianDate() {
        return julianDate;
    }

    public void setJulianDate(Long julianDate) {
        this.julianDate = julianDate;
    }

    public Long getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(Long bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        accNumber = accNumber.replaceAll("\\s", "");
        this.accNumber = accNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        cardNumber = cardNumber.replaceAll("\\s", "");
        this.cardNumber = cardNumber;
    }

    public String getCustLocalName() {
        return custLocalName;
    }

    public void setCustLocalName(String custLocalName) {
        custLocalName = custLocalName.replaceAll("\\s", "");
        this.custLocalName = custLocalName;
    }

    public String getCustGoccCode() {
        return custGoccCode;
    }

    public void setCustGoccCode(String custGoccCode) {
        custGoccCode = custGoccCode.replaceAll("\\s", "");
        this.custGoccCode = custGoccCode;
    }

    public String getCustEmpName() {
        return custEmpName;
    }

    public void setCustEmpName(String custEmpName) {
        custEmpName = custEmpName.replaceAll("\\s", "");
        this.custEmpName = custEmpName;
    }

    public String getAccountBlockCode() {
        return accountBlockCode;
    }

    public void setAccountBlockCode(String accountBlockCode) {
        accountBlockCode = accountBlockCode.replaceAll("\\s", "");
        this.accountBlockCode = accountBlockCode;
    }

    public String getCardBlockCode() {
        return cardBlockCode;
    }

    public void setCardBlockCode(String cardBlockCode) {
        cardBlockCode = cardBlockCode.replaceAll("\\s", "");
        this.cardBlockCode = cardBlockCode;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        cardStatus = cardStatus.replaceAll("\\s", "");
        this.cardStatus = cardStatus;
    }

    public Long getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Long txnDate) {
        this.txnDate = txnDate;
    }

    public Long getTxnPostingDate() {
        return txnPostingDate;
    }

    public String getTxnAuthCode() {
        return txnAuthCode;
    }

    public void setTxnAuthCode(String txnAuthCode) {
        this.txnAuthCode = txnAuthCode;
    }

    public void setTxnPostingDate(Long txnPostingDate) {
        this.txnPostingDate = txnPostingDate;
    }

    public Long getTxnMechNumber() {
        return txnMechNumber;
    }

    public void setTxnMechNumber(Long txnMechNumber) {
        this.txnMechNumber = txnMechNumber;
    }

    public String getTxnDesc() {
        return txnDesc;
    }

    public void setTxnDesc(String txnDesc) {
        txnDesc = txnDesc.replaceAll("\\s", "");
        this.txnDesc = txnDesc;
    }

    public Long getTxnMccCode() {
        return txnMccCode;
    }

    public void setTxnMccCode(Long txnMccCode) {
        this.txnMccCode = txnMccCode;
    }

    public Long getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(Long txnCode) {
        this.txnCode = txnCode;
    }

    public Double getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(Double txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Double getAccountPermCrLimit() {
        return accountPermCrLimit;
    }

    public void setAccountPermCrLimit(Double accountPermCrLimit) {
        this.accountPermCrLimit = accountPermCrLimit;
    }

    public Double getDoubleLimit() {
        return doubleLimit;
    }

    public void setDoubleLimit(Double doubleLimit) {
        this.doubleLimit = doubleLimit;
    }

    public String getTriggeredRule() {
        return triggeredRule;
    }

    public void setTriggeredRule(String triggeredRule) {
        this.triggeredRule = triggeredRule;
    }

}
