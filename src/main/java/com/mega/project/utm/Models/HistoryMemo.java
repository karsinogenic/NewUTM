package com.mega.project.utm.Models;

import java.time.LocalDateTime;

import com.mega.project.utm.Models.AMLA.RefundPoinC;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HISTORY_MEMO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryMemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "ruleId")
    private String ruleId;

    @Column(nullable = false)
    private String memo;

    @Column(nullable = false)
    private String reviewBy;

    private String approvedBy;

    @Column(nullable = false)
    private String status;

    private Boolean approvalStatus;

    private String rule;

    @Column(nullable = false)
    private LocalDateTime reviewDateTime;

    // @OneToOne
    // @JoinColumn(name = "ruleId", referencedColumnName = "id", insertable = false,
    // updatable = false)
    // private RuleResult ruleResult;

}
