package com.mega.project.utm.Models.AMLA;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MERCH_MENYIMPANG_B")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchMenyimpangB {

    @Id
    @GeneratedValue(generator = "coba")
    @GenericGenerator(name = "coba", strategy = "com.mega.project.utm.services.StringUUIDGenerator")
    private String id;

    private Long julianDate;

    private Long bankNumber;

    private Long merchNumber;

    private String merchLocalName;

    private String ownerMemo;

    private Long txnPostDate;

    private Double totalTrx;

    private Long frequency;

    private Long mcc;

    private String cardNumber;

    private String accountNumber;

    private String custLocalName;

    private Double creditLimit;

    private String triggeredRule;

    private LocalDate createdAt;

}
