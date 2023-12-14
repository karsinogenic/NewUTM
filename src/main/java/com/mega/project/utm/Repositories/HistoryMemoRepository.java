package com.mega.project.utm.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mega.project.utm.Models.HistoryMemo;
import com.mega.project.utm.Models.HistoryMerchTrans;

public interface HistoryMemoRepository extends JpaRepository<HistoryMemo, Long> {

    List<HistoryMemo> findByRuleId(String ruleId);

}
