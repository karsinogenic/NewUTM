package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mega.project.utm.Models.AMLA.MerchMenyimpangA;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangB;
import java.util.List;

public interface MerchMenyimpangB_Repository extends JpaRepository<MerchMenyimpangB, String> {
    List<MerchMenyimpangB> findByJulianDate(Long julianDate);

    @Query("Select mmb from MerchMenyimpangB mmb where id IN ?1")
    List<MerchMenyimpangB> findByListId(List<String> id);
}
