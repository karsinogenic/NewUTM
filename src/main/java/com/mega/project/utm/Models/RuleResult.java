package com.mega.project.utm.Models;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RULE_RESULT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleResult {

    @Id
    @Column(unique = true)
    @GeneratedValue(generator = "coba")
    @GenericGenerator(name = "coba", strategy = "com.mega.project.utm.services.StringUUIDGenerator")
    private String id;

    @Transient
    @Column(insertable = false, updatable = false)
    private Long hiddenId;

    private String mId;

    private String cardNum;

    private Integer count;

    private Long sum;

    private String rule;

    private LocalDate date;

    private String memo;

    private String owner;

    private String exclude;

    private String lockedBy;

    private String reviewBy;

    private String status;

}
