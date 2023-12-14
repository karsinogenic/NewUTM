package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.project.utm.Models.AMLA.MerchMenyimpangA;
import java.util.List;

public interface MerchMenyimpangA_Repository extends JpaRepository<MerchMenyimpangA, String> {
    List<MerchMenyimpangA> findByJulianDate(Long julianDate);
}
