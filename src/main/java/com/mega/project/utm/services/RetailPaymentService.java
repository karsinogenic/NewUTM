package com.mega.project.utm.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mega.project.utm.Models.MapAmlaSummary;
import com.mega.project.utm.Models.MapAmlaDetail;
import com.mega.project.utm.Models.util.ResultPaymentRetail;

@Service
public class RetailPaymentService {

    public List<MapAmlaSummary> removeExcludeRetailPayment(List<MapAmlaSummary> isi) {
        Integer[] retailcode = { 3500, 1005 };
        // System.out.println(isi.size());
        List<MapAmlaSummary> newisi = isi.stream()
                // .peek(e -> System.out.println("Initial: " + e))
                .filter(e -> (Arrays.asList(retailcode).contains(e.getOlstTxnCode()))
                        || (e.getOlstTxnCode() >= 8000 && e.getOlstTxnDesc().toUpperCase().contains("PAYMENT")))
                // .filter(e -> !e.getOlstTxnDesc().toUpperCase().contains("BONUS"))
                .collect(Collectors.toList());
        return newisi;
    }

    public List<MapAmlaDetail> removeBonus(List<MapAmlaDetail> isi) {
        // Integer[] retailcode = { 3500, 1005 };
        // System.out.println(isi.size());
        List<MapAmlaDetail> newisi = isi.stream()
                // .peek(e -> System.out.println("Initial: " + e))
                // .filter(e -> (Arrays.asList(retailcode).contains(e.getOlstTxnCode()))
                // || (e.getOlstTxnCode() >= 8000 &&
                // e.getOlstTxnDesc().toUpperCase().contains("PAYMENT")))
                .filter(e -> !e.getOlstTxnDesc().toUpperCase().contains("BONUS POIN"))
                .collect(Collectors.toList());
        return newisi;
    }

    public String recastToTypeTrx(Integer txncode, String desc) {
        Integer[] retailcode = { 3500, 1005 };
        if (Arrays.asList(retailcode).contains(txncode)) {
            return "Retail";
        } else if (txncode >= 8000 && desc.toUpperCase().contains("PAYMENT")) {
            return "Payment";
        } else {
            return "-";
        }
    }

    public List<ResultPaymentRetail> getTotalAmount(List<MapAmlaSummary> isi) {
        Integer[] retailcode = { 3500, 1005 };
        List<Integer> retailcodeList = Arrays.asList(retailcode);
        List<ResultPaymentRetail> hasilList = new ArrayList<>();

        // Custom container to hold intermediate results
        class Result {
            int retailFreq = 0;
            int retailTotal = 0;
            int paymentFreq = 0;
            int paymentTotal = 0;
        }

        // Collecting results in one pass
        Result result = isi.stream().collect(Result::new, (r, mapAmlaSummary) -> {
            if (retailcodeList.contains(mapAmlaSummary.getOlstTxnCode())) {
                r.retailFreq += mapAmlaSummary.getCount();
                r.retailTotal += mapAmlaSummary.getSum();
            } else {
                r.paymentFreq += mapAmlaSummary.getCount();
                r.paymentTotal += mapAmlaSummary.getSum();
            }
        }, (r1, r2) -> {
            r1.retailFreq += r2.retailFreq;
            r1.retailTotal += r2.retailTotal;
            r1.paymentFreq += r2.paymentFreq;
            r1.paymentTotal += r2.paymentTotal;
        });

        // Creating the final map with the results
        Map<String, Integer> hasil = new HashMap<>();
        hasil.put("retailFreq", result.retailFreq);
        hasil.put("retailTotal", result.retailTotal);
        hasil.put("paymentFreq", result.paymentFreq);
        hasil.put("paymentTotal", result.paymentTotal);

        hasilList.add(new ResultPaymentRetail("Retail", result.retailFreq, result.retailTotal));
        hasilList.add(new ResultPaymentRetail("Payment", result.paymentFreq, result.paymentTotal));

        return hasilList;
    }

}
