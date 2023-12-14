package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mega.project.utm.Models.HistoryMerchTrans;
import com.mega.project.utm.Models.TempHistoryMerchTrans;

import java.time.LocalDate;
import java.util.List;

public interface TempHistoryMerchTransRepository extends JpaRepository<TempHistoryMerchTrans, String> {

    TempHistoryMerchTrans findByKeyNo(String keyNo);

    // @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate = :pDate
    // and hmt.merchantId = :mId")
    // List<HistoryMerchTrans> findByMerchantIdWithDate(@Param("mId") String
    // merchantId, @Param("pDate") LocalDate pDate);

    @Query("select hmt from TempHistoryMerchTrans hmt where hmt.paymentDate = :pDate and hmt.triggeredRule like %:tr%")
    List<TempHistoryMerchTrans> findByTriggeredRuleWithDate(@Param("tr") String triggeredRule,
            @Param("pDate") LocalDate pDate);

}
