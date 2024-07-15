package com.mega.project.utm.Models.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultPaymentRetail {
    private String name;
    private Integer freq;
    private Integer amount;
}
