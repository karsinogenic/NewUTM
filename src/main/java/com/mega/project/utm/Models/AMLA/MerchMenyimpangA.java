package com.mega.project.utm.Models.AMLA;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.cglib.core.Local;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MERCH_MENYIMPANG_A")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchMenyimpangA {

    @Id
    @GeneratedValue(generator = "coba")
    @GenericGenerator(name = "coba", strategy = "com.mega.project.utm.services.StringUUIDGenerator")
    private String id;

    private Long julianDate;

    private Long bankNumber;

    private Long merchNumber;

    private String merchLocalName;

    private String ownerMemo;

    private Double txnAmount;

    private Long txnPostDate;

    private Double avgPerMonth;

    private Double avg200PerMonth;

    private String triggeredRule;

    private LocalDate createdAt;

}
