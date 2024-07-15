package com.mega.project.utm.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.project.utm.Models.AMLA.AmlaSaldoKredit;
import com.mega.project.utm.Models.AMLA.RefundPoin;

public interface SaldoKreditRepository extends JpaRepository<AmlaSaldoKredit, String> {

    List<AmlaSaldoKredit> findByJulianDate(Long julianDate);

    List<AmlaSaldoKredit> findByJulianDateAndTriggeredRule(Long julianDate, String rule);

}
