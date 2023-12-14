package com.mega.project.utm.Models.AMLA;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AMLA_RULE_RESULT")
public class AmlaRuleResult {

    @Id
    @GeneratedValue(generator = "coba")
    @GenericGenerator(name = "coba", strategy = "com.mega.project.utm.services.StringUUIDGenerator")
    private String id;

    @Column(name = "amla_id")
    private String amlaId;

    @Column(name = "triggered_rule")
    private String triggeredRule;

    @Column(name = "post_date")
    private LocalDate postDate;

    @Transient
    @Column(insertable = false, updatable = false)
    private Long hiddenId;

    private String memo;

    @Column(name = "review_by")
    private String reviewBy;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "locked_by")
    private String lockedBy;

    private String status;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_sent")
    private Boolean isSent;

}
