package com.mega.project.utm.Models;

import com.mega.project.utm.Models.AMLA.MerchMenyimpangA;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangB;
import com.mega.project.utm.Models.AMLA.RefundPoin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllData {

    RefundPoin refundPoin;
    MerchMenyimpangA menyimpangA;
    MerchMenyimpangB menyimpangB;
}
