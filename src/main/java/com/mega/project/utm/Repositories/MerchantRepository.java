package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.project.utm.Models.Merchant;
import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, String> {

    Merchant findByMerchNum(String merchNum);

}
