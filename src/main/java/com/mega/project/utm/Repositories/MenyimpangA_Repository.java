package com.mega.project.utm.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.project.utm.Models.AMLA.MenyimpangA;

public interface MenyimpangA_Repository extends JpaRepository<MenyimpangA, String> {

    List<MenyimpangA> findByJulianDate(Long valueOf);

}
