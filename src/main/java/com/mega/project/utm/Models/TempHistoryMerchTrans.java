package com.mega.project.utm.Models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TEMP_HISTORY_MSP_QR_CODE_DATA_HISTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempHistoryMerchTrans {

    @Id
    @Column(name = "Key_No")
    private String keyNo;

    @Column(name = "Merchant_Id")
    private String merchantId;

    @Column(name = "Terminal_Id")
    private String terminalId;

    @Column(name = "acct_source")
    private String accSource;

    @Column(name = "Card_Number")
    private String cardNumber;

    @Column(name = "No_Hp")
    private String noHp;

    @Column(name = "Amount")
    private Long amount;

    @Column(name = "Payment_Date")
    private LocalDate paymentDate;

    @Column(name = "Host_RespCode")
    private String hostRespCode;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "Trx_Type")
    private String trxType;

    @Column(name = "bit_43")
    private String bit43;

    @Column(name = "other_merch")
    private String otherMerch;

    @Column(name = "qr_val")
    private String qrVal;

    @Column(name = "reff_102")
    private String reff102;

    @Column(name = "Payment_Time")
    private LocalTime payementTime;

    @Column(name = "triggered_rule")
    private String triggeredRule;

    @Column(name = "Search_Group")
    private String searchId;
    // @ManyToOne
    // @JoinColumn(name = "Merchant_Id", referencedColumnName = "MECH_NBR",
    // insertable = false, updatable = false)
    // private Merchant merchant;

}
