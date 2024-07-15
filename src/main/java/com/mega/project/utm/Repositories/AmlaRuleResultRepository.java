package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mega.project.utm.Models.AMLA.AmlaMerchantRuleResult;
import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import java.util.List;
import java.time.LocalDate;

public interface AmlaRuleResultRepository extends JpaRepository<AmlaRuleResult, String> {
    List<AmlaRuleResult> findByPostDate(LocalDate postDate);

    @Query("select a from AmlaRuleResult a where a.isApproved = false or a.isApproved is NULL order by a.triggeredRule desc")
    List<AmlaRuleResult> findByPostDateNotApproved(LocalDate postDate);

    @Query("select a from AmlaRuleResult a where a.isSent = ?1 or a.isApproved is NULL order by a.memo,a.triggeredRule,a.id,a.postDate desc")
    List<AmlaRuleResult> findByIsSentAndIsApprovedIsNull(Boolean isSent);

    @Query("select a from AmlaRuleResult a where a.bankCode = 426 and a.isSent = ?1  and (a.isApproved is NULL or a.isApproved = false) order by a.memo desc")
    List<AmlaRuleResult> findByIsSentAndIsApprovedIsNullMega(Boolean isSent);

    @Query("select a from AmlaRuleResult a where a.bankCode != 426 and a.isSent = ?1 and (a.isApproved is NULL or a.isApproved = false) order by a.memo desc")
    List<AmlaRuleResult> findByIsSentAndIsApprovedIsNullSyariah(Boolean isSent);

    @Query("select a from AmlaRuleResult a where a.isApproved is not null")
    List<AmlaRuleResult> findByPostDateApproved(LocalDate postDate);

    List<AmlaRuleResult> findByIsSent(Boolean isSent);

    @Query("select a from AmlaRuleResult a where a.bankCode = 426 and a.isSent = ?1")
    List<AmlaRuleResult> findByIsSentMega(Boolean isSent);

    @Query("select a from AmlaRuleResult a where a.bankCode != 426 and a.isSent = ?1")
    List<AmlaRuleResult> findByIsSentSyariah(Boolean isSent);

    // List<AmlaRuleResult> findByIsApprovedIsNotNull();
}
