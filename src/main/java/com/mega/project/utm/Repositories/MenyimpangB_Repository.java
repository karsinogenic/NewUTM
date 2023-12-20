package com.mega.project.utm.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.project.utm.Models.AMLA.MenyimpangB;

public interface MenyimpangB_Repository extends JpaRepository<MenyimpangB, String> {

    List<MenyimpangB> findByJulianDate(Long valueOf);

}
