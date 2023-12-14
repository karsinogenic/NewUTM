package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mega.project.utm.Models.HistoryMerchTrans;

import java.time.LocalDate;
import java.util.List;

public interface HistoryMerchTransRepository extends JpaRepository<HistoryMerchTrans, String> {

    HistoryMerchTrans findByKeyNo(String keyNo);

    @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate = :pDate and hmt.merchantId = :mId")
    List<HistoryMerchTrans> findByMerchantIdWithDate(@Param("mId") String merchantId, @Param("pDate") LocalDate pDate);

    @Query("select hmt.keyNo from HistoryMerchTrans hmt where hmt.paymentDate = :pDate and hmt.merchantId =:mId")
    List<String> findKeyByMerchantIdWithDate(@Param("mId") String merchantId, @Param("pDate") LocalDate pDate);

    @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate = :pDate and hmt.cardNumber = :cardNum and hmt.merchantId =:mId order by hmt.paymentTime asc")
    List<HistoryMerchTrans> findKeyByMerchantIdWithDateNew(@Param("mId") String merchantId,
            @Param("cardNum") String cardNum,
            @Param("pDate") LocalDate pDate);

    @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate = :pDate and hmt.merchantId =:mId and hmt.cardNumber is NULL order by hmt.paymentTime asc")
    List<HistoryMerchTrans> findKeyByMerchantIdWithDateCardNull(@Param("mId") String merchantId,
            @Param("pDate") LocalDate pDate);

    @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate = :pDate and hmt.cardNumber = :cardNum and hmt.amount > 100000 and hmt.merchantId =:mId and hmt.keyNo NOT IN :excludeID order by hmt.paymentTime asc")
    List<HistoryMerchTrans> findKeyByMerchantIdWithDateNewQR001(@Param("mId") String merchantId,
            @Param("cardNum") String cardNum,
            @Param("pDate") LocalDate pDate, @Param("excludeID") List<String> excludeID);

    @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate = :pDate and hmt.merchantId =:mId and hmt.amount > 100000 and hmt.cardNumber is NULL and hmt.keyNo NOT IN :excludeID order by hmt.paymentTime asc")
    List<HistoryMerchTrans> findKeyByMerchantIdWithDateCardNullQR001(@Param("mId") String merchantId,
            @Param("pDate") LocalDate pDate, @Param("excludeID") List<String> excludeID);

    @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate = :pDate and hmt.cardNumber = :cardNum and hmt.amount > 100000 and hmt.merchantId =:mId order by hmt.paymentTime asc")
    List<HistoryMerchTrans> findByMerchantIdWithDateNewQR001(@Param("mId") String merchantId,
            @Param("cardNum") String cardNum,
            @Param("pDate") LocalDate pDate);

    @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate = :pDate and hmt.merchantId =:mId and hmt.amount > 100000 and hmt.cardNumber is NULL order by hmt.paymentTime asc")
    List<HistoryMerchTrans> findByMerchantIdWithDateCardNullQR001(@Param("mId") String merchantId,
            @Param("pDate") LocalDate pDate);

    @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate > :pDate and hmt.merchantId =:mId")
    List<HistoryMerchTrans> findDetail(@Param("mId") String merchantId, @Param("pDate") LocalDate pDate);

    @Query("select hmt from HistoryMerchTrans hmt where hmt.keyNo IN ?1")
    List<HistoryMerchTrans> findByListId(List<String> list);

    @Query("select hmt.amount from HistoryMerchTrans hmt where hmt.keyNo IN ?1")
    List<Long> findAmount(List<String> list);

    // @Query("select hmt from HistoryMerchTrans hmt where hmt.paymentDate = :pDate
    // and hmt.triggeredRule = :tr")
    // List<HistoryMerchTrans> findByTriggeredRuleWithDate(@Param("tr") String
    // triggeredRule,
    // @Param("pDate") LocalDate pDate);

}
