package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.project.utm.Models.AMLA.MerchMenyimpangB;
import java.util.List;

public interface MerchMenyimpangB_Repository extends JpaRepository<MerchMenyimpangB, String> {
    List<MerchMenyimpangB> findByJulianDate(Long julianDate);
}
