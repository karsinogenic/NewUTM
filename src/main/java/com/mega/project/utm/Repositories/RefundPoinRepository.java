package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.project.utm.Models.AMLA.RefundPoin;
import com.mega.project.utm.Models.AMLA.RefundPoinC;
import java.util.List;

public interface RefundPoinRepository extends JpaRepository<RefundPoin, String> {
    List<RefundPoin> findByJulianDate(Long julianDate);
}