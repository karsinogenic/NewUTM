package com.mega.project.utm.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mega.project.utm.Models.HistoryMemo;
import com.mega.project.utm.Models.HistoryMerchTrans;
import com.mega.project.utm.Models.QR002;
import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Models.TempHistoryMerchTrans;
import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangA;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangB;
import com.mega.project.utm.Models.AMLA.RefundPoin;
import com.mega.project.utm.Repositories.AmlaRuleResultRepository;
import com.mega.project.utm.Repositories.HistoryMemoRepository;
import com.mega.project.utm.Repositories.HistoryMerchTransRepository;
import com.mega.project.utm.Repositories.MerchMenyimpangA_Repository;
import com.mega.project.utm.Repositories.MerchMenyimpangB_Repository;
import com.mega.project.utm.Repositories.RefundPoinRepository;
import com.mega.project.utm.Repositories.RuleResultRepository;
import com.mega.project.utm.Repositories.TempHistoryMerchTransRepository;
import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class CustomRule {

    private JdbcQueryService jdbcQueryService;
    private HistoryMerchTransRepository historyMerchTransRepository;
    private TempHistoryMerchTransRepository tempHistoryMerchTransRepository;
    private HistoryMemoRepository historyMemoRepository;
    private RuleResultRepository ruleResultRepository;
    private RefundPoinRepository refundPoinRepository;
    private MerchMenyimpangA_Repository merchMenyimpangA_Repository;
    private MerchMenyimpangB_Repository merchMenyimpangB_Repository;
    private AmlaRuleResultRepository amlaRuleResultRepository;

    public CustomRule(JdbcQueryService jdbcQueryService, HistoryMerchTransRepository historyMerchTransRepository,
            TempHistoryMerchTransRepository tempHistoryMerchTransRepository,
            HistoryMemoRepository historyMemoRepository, RuleResultRepository ruleResultRepository,
            RefundPoinRepository refundPoinRepository) {
        this.jdbcQueryService = jdbcQueryService;
        this.historyMerchTransRepository = historyMerchTransRepository;
        this.tempHistoryMerchTransRepository = tempHistoryMerchTransRepository;
        this.historyMemoRepository = historyMemoRepository;
        this.ruleResultRepository = ruleResultRepository;
        this.refundPoinRepository = refundPoinRepository;

    }

    public MerchMenyimpangA_Repository getMerchMenyimpangA_Repository() {
        return merchMenyimpangA_Repository;
    }

    public void setMerchMenyimpangA_Repository(MerchMenyimpangA_Repository merchMenyimpangA_Repository) {
        this.merchMenyimpangA_Repository = merchMenyimpangA_Repository;
    }

    public MerchMenyimpangB_Repository getMerchMenyimpangB_Repository() {
        return merchMenyimpangB_Repository;
    }

    public void setMerchMenyimpangB_Repository(MerchMenyimpangB_Repository merchMenyimpangB_Repository) {
        this.merchMenyimpangB_Repository = merchMenyimpangB_Repository;
    }

    private List<Long> amounts = new ArrayList<>();

    private List<QR002> QR002 = new ArrayList<>();

    private List<RuleResult> NEW_QR002 = new ArrayList<>();

    public List<QR002> QR002(LocalDate ldt) {
        List<Map<String, Object>> hasil = new ArrayList<>();
        List<QR002> new_hasil = new ArrayList<>();
        String query = """
                select "Merchant_Id" ,"Card_Number",count(*) as count from "HISTORY_MSP_QR_CODE_DATA_HISTORY" where "Payment_Date" = '
                """
                + ldt.toString() + """
                        ' group by "Merchant_Id","Card_Number" having count(*)  > 10;
                        """;
        System.out.println(query);
        try {
            hasil = jdbcQueryService.executeCustomQuery(query);
            // System.out.println(hasil.size());
            for (Map<String, Object> map : hasil) {
                String mch_id = map.get("Merchant_Id") == null ? ""
                        : map.get("Merchant_Id").toString().replaceAll("^0+", "");
                String card_nbr = map.get("Card_Number") == null ? "" : map.get("Card_Number").toString();
                String jmlh_trx = map.get("count") == null ? "" : map.get("count").toString();
                // System.out.println(map.get("Merchant_Id").toString().replaceAll("^0+", ""));
                QR002 coba = new QR002(mch_id, card_nbr, jmlh_trx);

                // System.out.println(mch_id);
                // System.out.println(card_nbr);
                // System.out.println(jmlh_trx);

                // coba.put("Key_No", map.get("Key_No"));
                // coba.put("Merchant_Id", map.get("Merchant_Id").toString().replaceAll("^0+",
                // ""));
                // coba.put("Card_Number", map.get("Card_Number"));
                // coba.put("Jumlah_Trx", map.get("count"));
                new_hasil.add(coba);
            }
            QR002 = new_hasil;

        } catch (Exception e) {
            // System.out.println("tai");
        }
        return new_hasil;
    }

    public List<RuleResult> NEW_QR002(LocalDate ldt, String kode) {
        List<Map<String, Object>> hasil = new ArrayList<>();
        List<RuleResult> new_hasil = new ArrayList<>();
        CustomQuery cq = new CustomQuery(ldt);
        String query = cq.QR002();
        if (kode != null) {
            query = cq.myQuery(kode);
        }

        System.out.println(query);
        try {
            hasil = jdbcQueryService.executeCustomQuery(query);
            // System.out.println(hasil.size());
            for (Map<String, Object> map : hasil) {
                String mch_id = map.get("Merchant_Id") == null ? ""
                        : map.get("Merchant_Id").toString().replaceAll("^0+", "");
                String card_nbr = map.get("Card_Number") == null ? "" : map.get("Card_Number").toString();
                String jmlh_trx = map.get("count") == null ? "0" : map.get("count").toString();
                String jmlh_trx_amount = map.get("sum") == null ? "0" : map.get("sum").toString();
                // System.out.println(map.get("Merchant_Id").toString().replaceAll("^0+", ""));
                RuleResult coba = new RuleResult();
                if (!card_nbr.equals("")) {
                    coba.setCardNum(card_nbr);
                }

                if (!jmlh_trx.equals("")) {
                    coba.setCount(Integer.valueOf(jmlh_trx));
                }

                if (!mch_id.equals("")) {
                    coba.setMId(mch_id);
                }
                coba.setDate(ldt);

                if (!jmlh_trx_amount.equals("")) {
                    coba.setSum(Long.valueOf(jmlh_trx_amount));
                }

                coba.setRule(kode != null ? kode : "QR002");
                if (map.get("owner") != null) {
                    coba.setOwner(map.get("owner").toString());
                }
                // if (this.ruleResultRepository.findbyDateAndRule(ldt,kode).isEmpty()) {
                // this.ruleResultRepository.save(coba);
                // }
                new_hasil.add(coba);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new_hasil;
    }

    public List<RuleResult> QR001() {
        List<RuleResult> hasil = new ArrayList<>();

        return hasil;
    }

    public List<HistoryMerchTrans> NewDetailQR002(String Id) {
        Optional<RuleResult> ruleOptional = this.ruleResultRepository.findById(Id);
        RuleResult ruleResult = ruleOptional.get();

        List<HistoryMerchTrans> finalHistoryMerchTrans = new ArrayList<>();
        // List<RuleResult> listRuleResults = this.NEW_QR002(ruleResult.getDate(),
        // null);
        // System.out.println("size QR002: " + QR002.size());
        List<String> historyMerchTrans = new ArrayList<>();

        String mIdNew = ruleResult.getMId();
        String finalMid = "0".repeat(11).substring(0, 11 - mIdNew.length()) + mIdNew;
        String cardNum = ruleResult.getCardNum();
        if (ruleResult.getRule().equals("QR002")
                || (ruleResult.getRule().equals("QR001") && ruleResult.getExclude() != null)) {
            if (cardNum == null) {
                if (ruleResult.getRule().equals("QR002")) {
                    finalHistoryMerchTrans = this.historyMerchTransRepository
                            .findKeyByMerchantIdWithDateCardNull(finalMid, ruleResult.getDate());
                } else {
                    List<String> excludeID = Arrays.asList(ruleResult.getExclude().split(","));
                    finalHistoryMerchTrans = this.historyMerchTransRepository
                            .findKeyByMerchantIdWithDateCardNullQR001(finalMid, ruleResult.getDate(), excludeID);

                }
            } else {
                if (ruleResult.getRule().equals("QR002")) {
                    finalHistoryMerchTrans = this.historyMerchTransRepository
                            .findKeyByMerchantIdWithDateNew(finalMid, ruleResult.getCardNum(), ruleResult.getDate());

                } else {
                    List<String> excludeID = Arrays.asList(ruleResult.getExclude().split(","));
                    finalHistoryMerchTrans = this.historyMerchTransRepository
                            .findKeyByMerchantIdWithDateNewQR001(finalMid, ruleResult.getCardNum(),
                                    ruleResult.getDate(), excludeID);

                }

            }
        } else if (ruleResult.getRule().equals("QR001") && ruleResult.getExclude() == null) {
            List<HistoryMerchTrans> tempHistoryMerchTrans = new ArrayList<>();
            List<String> ls = new ArrayList<>();
            if (cardNum == null) {
                // System.out.println("execute1");
                tempHistoryMerchTrans = this.historyMerchTransRepository
                        .findByMerchantIdWithDateCardNullQR001(finalMid, ruleResult.getDate());
            } else {
                // System.out.println("execute2");

                tempHistoryMerchTrans = this.historyMerchTransRepository
                        .findByMerchantIdWithDateNewQR001(finalMid, ruleResult.getCardNum(), ruleResult.getDate());
            }

            List<String> excludeId = new ArrayList<>();
            List<String> includeId = new ArrayList<>();
            Long sum = (long) 0;
            // System.out.println("tempHistoryMerchTrans: " + tempHistoryMerchTrans.size());
            for (int i = 0; i < tempHistoryMerchTrans.size(); i++) {
                if (i != tempHistoryMerchTrans.size() - 1) {
                    LocalTime time1 = tempHistoryMerchTrans.get(i).getPaymentTime();
                    LocalTime time2 = tempHistoryMerchTrans.get(i + 1).getPaymentTime();
                    // System.out.print(Id + " : ");
                    // System.out.println("time2: " + time2 + " time1: " + time1);
                    // System.out.println(MINUTES.between(time1, time2));
                    if (MINUTES.between(time1, time2) > 5) {
                        // System.out.println("> 5 menit");
                        excludeId.add(tempHistoryMerchTrans.get(i).getKeyNo());
                        excludeId.add(tempHistoryMerchTrans.get(i + 1).getKeyNo());
                    } else {
                        includeId.add(tempHistoryMerchTrans.get(i).getKeyNo());
                        includeId.add(tempHistoryMerchTrans.get(i + 1).getKeyNo());
                    }

                }
            }
            JSONArray jsonArray = new JSONArray(excludeId);
            JSONArray jsonInclude = new JSONArray(includeId);
            includeId.stream().distinct();
            List<Long> newSum = this.historyMerchTransRepository.findAmount(includeId);
            if (newSum.size() > 0) {
                sum = newSum.stream().mapToLong(Long::longValue).sum();
            }
            // System.out.println("exclued: " + jsonArray.toString());
            // System.out.println("include : " + jsonInclude.toString());
            excludeId.removeAll(includeId);
            excludeId.stream().distinct();
            ruleResult.setSum(sum);

            JSONArray jsonExclude = new JSONArray(excludeId);

            String finalExclude = jsonExclude.toString().replace("\"", "").replace("[", "").replace("]", "");

            ruleResult.setExclude(finalExclude);
            if (sum == 0) {
                // System.out.println("delete" + Id + " : " + ruleResult.getCount() + " : " +
                // excludeId.size());
                this.ruleResultRepository.delete(ruleResult);
            } else {
                // System.out.println("save " + Id + " : " + ruleResult.getCount() + " : " +
                // excludeId.size());
                ruleResult.setCount(ruleResult.getCount() - excludeId.size());
                this.ruleResultRepository.save(ruleResult);
            }
            finalHistoryMerchTrans = tempHistoryMerchTrans;

        } else {
            LocalDate newLdt = ruleResult.getDate();
            if (ruleResult.getRule().equals("QR003")) {
                // System.out.println("masuk");
                newLdt = newLdt.minusDays(7);
            }
            finalHistoryMerchTrans = this.historyMerchTransRepository
                    .findDetail(finalMid, newLdt);
        }
        // System.out.println(finalMid);

        // List<HistoryMerchTrans> histMemo =
        // this.historyMerchTransRepository.findByListId(historyMerchTrans);
        // for (HistoryMerchTrans historyMemo : histMemo) {
        // amounts.add(historyMemo.getAmount());
        // }

        // finalHistoryMerchTrans = histMemo;
        // System.out.println("size detail: " + finalHistoryMerchTrans);

        // System.out.println("size detail: " + finalHistoryMerchTrans);

        return finalHistoryMerchTrans;

    }

    public List<RuleResult> allRule(LocalDate ldt) {
        List<RuleResult> allRule = new ArrayList<>();
        List<RuleResult> newAllRule = this.ruleResultRepository.findByDate(ldt);
        // allRule.addAll(NEW_QR002(ldt, "QR006"));
        if (newAllRule.isEmpty()) {
            CustomQuery customQuery = new CustomQuery(ldt);
            allRule.addAll(NEW_QR002(ldt, "QR001"));
            allRule.addAll(NEW_QR002(ldt, "QR002"));
            allRule.addAll(NEW_QR002(ldt, "QR003"));
            this.ruleResultRepository.saveAll(allRule);
            List<RuleResult> listQR001 = this.ruleResultRepository.findbyDateAndRule(ldt, "QR001");
            for (RuleResult ruleResult : listQR001) {
                NewDetailQR002(ruleResult.getId());
            }
        }
        newAllRule = this.ruleResultRepository.findByDateNotGroup(ldt);
        return newAllRule;
    }

    public List<Long> getAmount() {
        return amounts;
    }

    public List<AmlaRuleResult> allAmlaRule(LocalDate lDate) {
        List<AmlaRuleResult> hasil = new ArrayList<>();
        // CustomQuery customQuery = new CustomQuery(lDate);
        // List<Map<String, Object>> trm002 =
        // jdbcQueryService.executeCustomQuery(customQuery.TRM002());

        return hasil;

    }

    public List<AmlaRuleResult> postToRefundPoin(List<Map<String, Object>> data, String rule, LocalDate localDate) {
        List<RefundPoin> refundPoins = new ArrayList<>();
        List<AmlaRuleResult> hasil = new ArrayList<>();
        String julianDate = localDate.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        if (this.refundPoinRepository.findByJulianDate(Long.valueOf(julianDate)).isEmpty()) {
            for (Map isi : data) {
                String randUUID = UUID.randomUUID().toString();
                RefundPoin refundPoin = new RefundPoin();
                refundPoin.setJulianDate(Long.valueOf(isi.get("julian_mis_date").toString()));
                refundPoin.setBankNumber(Long.valueOf(isi.get("txn_bank_nbr").toString()));
                refundPoin.setAccNumber(isi.get("txn_acct_nbr").toString());
                refundPoin.setCardNumber(isi.get("txn_card_nbr").toString());
                refundPoin.setCustLocalName(isi.get("cust_local_name").toString());
                refundPoin.setCustGoccCode(isi.get("cust_gocc_code").toString());
                refundPoin.setCustEmpName(isi.get("cust_emp_name").toString());
                refundPoin.setAccountBlockCode(isi.get("crdacct_blk_code").toString());
                refundPoin.setCardBlockCode(isi.get("card_blk_code").toString());
                refundPoin.setCardStatus(isi.get("card_status").toString());
                refundPoin.setTxnDate(Long.valueOf(isi.get("txn_dte").toString()));
                refundPoin.setTxnPostingDate(Long.valueOf(isi.get("txn_posting_dte").toString()));
                refundPoin.setTxnMechNumber(Long.valueOf(isi.get("txn_mech_nbr").toString()));
                refundPoin.setTxnDesc(isi.get("txn_desc").toString());
                refundPoin.setTxnMccCode(Long.valueOf(isi.get("txn_mcc_code").toString()));
                refundPoin.setTxnCode(Long.valueOf(isi.get("txn_code").toString()));
                refundPoin.setTxnAuthCode(isi.get("txn_auth_code").toString());
                refundPoin.setTxnAmount(Double.valueOf(isi.get("txn_amt").toString()));
                refundPoin.setAccountPermCrLimit(Double.valueOf(isi.get("crdacct_perm_crlimit").toString()));
                refundPoin.setDoubleLimit(Double.valueOf(isi.get("double_limit").toString()));
                refundPoin.setTriggeredRule(rule);
                refundPoin.setCreatedAt(LocalDate.now());
                refundPoins.add(refundPoin);
            }
            this.refundPoinRepository.saveAll(refundPoins);
        }
        refundPoins = this.refundPoinRepository.findByJulianDate(Long.valueOf(julianDate));
        for (RefundPoin isix : refundPoins) {
            AmlaRuleResult amlaRuleResult = new AmlaRuleResult();
            amlaRuleResult.setAmlaId(isix.getId());
            amlaRuleResult.setPostDate(localDate);
            amlaRuleResult.setTriggeredRule(isix.getTriggeredRule());
            // amlaRuleResult.setIsLocked(false);
            amlaRuleResult.setIsSent(false);
            hasil.add(amlaRuleResult);
        }
        return hasil;
    }

    public AmlaRuleResultRepository getAmlaRuleResultRepository() {
        return amlaRuleResultRepository;
    }

    public void setAmlaRuleResultRepository(AmlaRuleResultRepository amlaRuleResultRepository) {
        this.amlaRuleResultRepository = amlaRuleResultRepository;
    }

    public List<AmlaRuleResult> allAmla(LocalDate localDate) {
        List<AmlaRuleResult> hasil = new ArrayList<>();
        CustomQuery customQuery = new CustomQuery(localDate);
        System.out.println("localdate: " + localDate.toString());
        List<Map<String, Object>> trm006 = jdbcQueryService.executeCustomQuery(customQuery.TRM006());
        List<AmlaRuleResult> trm006Amla = this.postToRefundPoin(trm006, "TRM006", localDate);
        List<Map<String, Object>> trm004 = jdbcQueryService.executeCustomQuery(customQuery.TRM004());
        List<AmlaRuleResult> trm004Amla = this.postToMerchantMenyimpangA(trm004, "TRM004", localDate);
        List<Map<String, Object>> trm005 = jdbcQueryService.executeCustomQuery(customQuery.TRM005());
        List<AmlaRuleResult> trm005Amla = this.postToMerchantMenyimpangB(trm005, "TRM005", localDate);
        // System.out.println(trm004Amla.size());
        // System.out.println(trm005Amla.size());
        // System.out.println(trm006Amla.size());
        hasil.addAll(trm005Amla);
        hasil.addAll(trm004Amla);
        hasil.addAll(trm006Amla);
        if (this.amlaRuleResultRepository.findByPostDate(localDate).isEmpty()) {
            this.amlaRuleResultRepository.saveAll(hasil);
        }
        System.out.println("size: " + hasil.size());

        // List<Map<String, Object>> hasil1 = jdbcQueryService.executeCustomQuery();
        return hasil;
    }

    private List<AmlaRuleResult> postToMerchantMenyimpangB(List<Map<String, Object>> trm005, String string,
            LocalDate localDate) {
        List<MerchMenyimpangB> merchB = new ArrayList<>();
        List<AmlaRuleResult> hasil = new ArrayList<>();
        String julianDate = localDate.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        if (this.merchMenyimpangB_Repository.findByJulianDate(Long.valueOf(julianDate)).isEmpty()) {
            for (Map isi : trm005) {
                String randUUID = UUID.randomUUID().toString();
                MerchMenyimpangB merchMenyimpangB = new MerchMenyimpangB();
                merchMenyimpangB.setMcc(Long.valueOf(isi.get("mcc").toString()));
                merchMenyimpangB.setFrequency(Long.valueOf(isi.get("freq").toString()));
                merchMenyimpangB.setBankNumber(Long.valueOf(isi.get("txnd_bank_nbr").toString()));
                merchMenyimpangB.setJulianDate(Long.valueOf(isi.get("julian_mis_date").toString()));
                merchMenyimpangB.setOwnerMemo(isi.get("mbpf_owner1_memo").toString());
                merchMenyimpangB.setTriggeredRule(string);
                merchMenyimpangB.setMerchLocalName(isi.get("mech_lcl_name").toString());
                merchMenyimpangB.setMerchNumber(Long.valueOf(isi.get("txnd_mech_nbr").toString()));
                merchMenyimpangB.setTotalTrx(Double.valueOf(isi.get("tot_trx").toString()));
                merchMenyimpangB.setTxnPostDate(Long.valueOf(isi.get("txnd_post_date").toString()));
                merchMenyimpangB.setCardNumber(isi.get("txnd_card_nbr").toString());
                merchMenyimpangB
                        .setAccountNumber(isi.get("crdacct_nbr") == null ? null : isi.get("crdacct_nbr").toString());
                merchMenyimpangB.setCreditLimit(isi.get("crdacct_perm_crlimit") == null ? null
                        : Double.valueOf(isi.get("crdacct_perm_crlimit").toString()));
                merchMenyimpangB.setCustLocalName(
                        isi.get("cust_local_name") == null ? null : isi.get("cust_local_name").toString());
                merchMenyimpangB.setCreatedAt(LocalDate.now());
                merchB.add(merchMenyimpangB);
            }
            this.merchMenyimpangB_Repository.saveAll(merchB);
        }
        merchB = this.merchMenyimpangB_Repository.findByJulianDate(Long.valueOf(julianDate));
        for (MerchMenyimpangB isix : merchB) {
            AmlaRuleResult amlaRuleResult = new AmlaRuleResult();
            amlaRuleResult.setAmlaId(isix.getId());
            amlaRuleResult.setPostDate(localDate);
            amlaRuleResult.setTriggeredRule(isix.getTriggeredRule());
            // amlaRuleResult.setIsLocked(false);
            amlaRuleResult.setIsSent(false);
            hasil.add(amlaRuleResult);
        }
        return hasil;
    }

    private List<AmlaRuleResult> postToMerchantMenyimpangA(List<Map<String, Object>> trm004, String string,
            LocalDate localDate) {
        List<MerchMenyimpangA> merchA = new ArrayList<>();
        List<AmlaRuleResult> hasil = new ArrayList<>();
        String julianDate = localDate.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        if (this.merchMenyimpangA_Repository.findByJulianDate(Long.valueOf(julianDate)).isEmpty()) {
            for (Map isi : trm004) {
                String randUUID = UUID.randomUUID().toString();
                MerchMenyimpangA merchMenyimpangA = new MerchMenyimpangA();
                merchMenyimpangA.setAvg200PerMonth(Double.valueOf(isi.get("avg_per_mth_200").toString()));
                merchMenyimpangA.setAvgPerMonth(Double.valueOf(isi.get("avg_per_mth").toString()));
                merchMenyimpangA.setBankNumber(Long.valueOf(isi.get("txnh_bank_nbr").toString()));
                merchMenyimpangA.setJulianDate(Long.valueOf(isi.get("julian_mis_date").toString()));
                merchMenyimpangA.setOwnerMemo(isi.get("mbpf_owner1_memo").toString());
                merchMenyimpangA.setTriggeredRule(string);
                merchMenyimpangA.setMerchLocalName(isi.get("mech_lcl_name").toString());
                merchMenyimpangA.setMerchNumber(Long.valueOf(isi.get("txnh_mech_nbr").toString()));
                merchMenyimpangA.setTxnAmount(Double.valueOf(isi.get("txnh_txn_amt").toString()));
                merchMenyimpangA.setTxnPostDate(Long.valueOf(isi.get("txnh_post_date").toString()));
                merchMenyimpangA.setCreatedAt(LocalDate.now());
                merchA.add(merchMenyimpangA);
            }
            this.merchMenyimpangA_Repository.saveAll(merchA);
        }
        merchA = this.merchMenyimpangA_Repository.findByJulianDate(Long.valueOf(julianDate));
        for (MerchMenyimpangA isix : merchA) {
            AmlaRuleResult amlaRuleResult = new AmlaRuleResult();
            amlaRuleResult.setAmlaId(isix.getId());
            amlaRuleResult.setPostDate(localDate);
            amlaRuleResult.setTriggeredRule(isix.getTriggeredRule());
            // amlaRuleResult.setIsLocked(false);
            amlaRuleResult.setIsSent(false);
            hasil.add(amlaRuleResult);
        }
        return hasil;
    }

    public List<Map<String, Object>> executeAmla(String query) {
        List<Map<String, Object>> hasilQuery = jdbcQueryService.executeCustomQuery(query);
        return hasilQuery;
    }

}
