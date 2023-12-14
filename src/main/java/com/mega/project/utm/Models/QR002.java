package com.mega.project.utm.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QR002 {

    private String merchant_id;
    private String card_number;
    private String jumlah_trx;

}
