package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mega.project.utm.Models.RuleResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RuleResultRepository extends JpaRepository<RuleResult, String> {

    @Query("Select rr from RuleResult rr where rr.cardNum = :cardNum and rr.date = :ldt and rr.mId = :mId and rr.rule = :kode")
    List<RuleResult> findQR002(@Param("cardNum") String cardNum, @Param("ldt") LocalDate ldt,
            @Param("mId") String mId, @Param("kode") String kode);

    Optional<RuleResult> findById(String id);

    @Query("Select rr from RuleResult rr where rr.date = ?1 and (rr.owner NOT LIKE 'GROUP%' or rr.owner is null) and rr.reviewBy is null")
    List<RuleResult> findByDateNotGroup(LocalDate date);

    List<RuleResult> findByDate(LocalDate date);

    List<RuleResult> findByLockedBy(String lockedBy);

    @Query("Select rr from RuleResult rr where rr.date = :ldt and rr.rule = :kode")
    List<RuleResult> findbyDateAndRule(@Param("ldt") LocalDate date, @Param("kode") String rule);

    List<RuleResult> findAllByMemoIsNull();

    List<RuleResult> findByDateAndMemoIsNotNull(LocalDate date);

}
