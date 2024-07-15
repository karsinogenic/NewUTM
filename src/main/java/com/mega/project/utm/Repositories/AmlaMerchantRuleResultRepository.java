package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mega.project.utm.Models.AMLA.AmlaMerchantRuleResult;
import java.util.List;
import java.time.LocalDate;

public interface AmlaMerchantRuleResultRepository extends JpaRepository<AmlaMerchantRuleResult, String> {
    List<AmlaMerchantRuleResult> findByPostDate(LocalDate postDate);

    @Query("select a from AmlaMerchantRuleResult a where a.isApproved = false or a.isApproved is NULL order by a.triggeredRule desc")
    List<AmlaMerchantRuleResult> findByPostDateNotApproved(LocalDate postDate);

    @Query("select a from AmlaMerchantRuleResult a where a.isSent = ?1 or a.isApproved is NULL order by a.triggeredRule,a.id,a.postDate desc")
    List<AmlaMerchantRuleResult> findByIsSentAndIsApprovedIsNull(Boolean isSent);

    @Query("select a from AmlaMerchantRuleResult a where a.bankCode = 426 and (a.isSent = ?1 or a.isApproved is NULL) order by a.triggeredRule,a.id,a.postDate desc")
    List<AmlaMerchantRuleResult> findByIsSentAndIsApprovedIsNullMega(Boolean isSent);

    @Query("select a from AmlaMerchantRuleResult a where a.bankCode != 426 and (a.isSent = ?1 or a.isApproved is NULL) order by a.triggeredRule,a.id,a.postDate desc")
    List<AmlaMerchantRuleResult> findByIsSentAndIsApprovedIsNullSyariah(Boolean isSent);

    @Query("select a.amlaId from AmlaMerchantRuleResult a where a.isSent = ?1 or a.isApproved is NULL order by a.triggeredRule,a.id,a.postDate desc")
    List<String> findAmlaIdByIsSentAndIsApprovedIsNull(Boolean isSent);

    @Query("select a from AmlaMerchantRuleResult a where a.isApproved is not null")
    List<AmlaMerchantRuleResult> findByPostDateApproved(LocalDate postDate);

    List<AmlaMerchantRuleResult> findByIsSent(Boolean isSent);

    @Query("select a from AmlaMerchantRuleResult a where a.bankCode = 426 and a.isSent = ?1")
    List<AmlaMerchantRuleResult> findByIsSentMega(Boolean isSent);

    @Query("select a from AmlaMerchantRuleResult a where a.bankCode != 426 and a.isSent = ?1")
    List<AmlaMerchantRuleResult> findByIsSentSyariah(Boolean isSent);

    // List<AmlaRuleResult> findByIsApprovedIsNotNull();
}
