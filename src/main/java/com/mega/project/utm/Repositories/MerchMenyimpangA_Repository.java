package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mega.project.utm.Models.AMLA.MenyimpangA;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangA;
import java.util.List;

public interface MerchMenyimpangA_Repository extends JpaRepository<MerchMenyimpangA, String> {
    List<MerchMenyimpangA> findByJulianDate(Long julianDate);

    @Query("Select mma from MerchMenyimpangA mma where id IN ?1")
    List<MerchMenyimpangA> findByListId(List<String> id);
}
