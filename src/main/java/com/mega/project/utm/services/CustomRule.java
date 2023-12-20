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
import com.mega.project.utm.Models.AMLA.MenyimpangA;
import com.mega.project.utm.Models.AMLA.MenyimpangB;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangA;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangB;
import com.mega.project.utm.Models.AMLA.RefundPoin;
import com.mega.project.utm.Repositories.AmlaRuleResultRepository;
import com.mega.project.utm.Repositories.HistoryMemoRepository;
import com.mega.project.utm.Repositories.HistoryMerchTransRepository;
import com.mega.project.utm.Repositories.MenyimpangA_Repository;
import com.mega.project.utm.Repositories.MenyimpangB_Repository;
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
    private MenyimpangA_Repository menyimpangA_Repository;
    private MenyimpangB_Repository menyimpangB_Repository;

    public CustomRule(JdbcQueryService jdbcQueryService, HistoryMerchTransRepository historyMerchTransRepository,
            TempHistoryMerchTransRepository tempHistoryMerchTransRepository,
            HistoryMemoRepository historyMemoRepository, RuleResultRepository ruleResultRepository,
            RefundPoinRepository refundPoinRepository, MenyimpangA_Repository menyimpangA_Repository,
            MenyimpangB_Repository menyimpangB_Repository) {
        this.jdbcQueryService = jdbcQueryService;
        this.historyMerchTransRepository = historyMerchTransRepository;
        this.tempHistoryMerchTransRepository = tempHistoryMerchTransRepository;
        this.historyMemoRepository = historyMemoRepository;
        this.ruleResultRepository = ruleResultRepository;
        this.refundPoinRepository = refundPoinRepository;
        this.menyimpangA_Repository = menyimpangA_Repository;
        this.menyimpangB_Repository = menyimpangB_Repository;

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

    public List<AmlaRuleResult> postToRefundPoin(List<Map<String, Object>> data, String rule, LocalDate localDate) {
        List<RefundPoin> refundPoins = new ArrayList<>();
        List<AmlaRuleResult> hasil = new ArrayList<>();
        String julianDate = localDate.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        if (this.refundPoinRepository.findByJulianDate(Long.valueOf(julianDate)).isEmpty()) {
            for (Map isi : data) {
                String randUUID = UUID.randomUUID().toString();
                RefundPoin refundPoin = new RefundPoin();
                refundPoin.setJulianDate(getValueLong(isi, "julian_mis_date"));
                refundPoin.setBankNumber(getValueLong(isi, "txn_bank_nbr"));
                refundPoin.setAccNumber(getValueString(isi, "txn_acct_nbr"));
                refundPoin.setCardNumber(getValueString(isi, "txn_card_nbr"));
                refundPoin.setCustLocalName(getValueString(isi, "cust_local_name"));
                refundPoin.setCustGoccCode(getValueString(isi, "cust_gocc_code"));
                refundPoin.setCustEmpName(getValueString(isi, "cust_emp_name"));
                refundPoin.setAccountBlockCode(getValueString(isi, "crdacct_blk_code"));
                refundPoin.setCardBlockCode(getValueString(isi, "card_blk_code"));
                refundPoin.setCardStatus(getValueString(isi, "card_status"));
                refundPoin.setTxnDate(getValueLong(isi, "txn_dte"));
                refundPoin.setTxnPostingDate(getValueLong(isi, "txn_posting_dte"));
                refundPoin.setTxnMechNumber(getValueLong(isi, "txn_mech_nbr"));
                refundPoin.setTxnDesc(getValueString(isi, "txn_desc"));
                refundPoin.setTxnMccCode(getValueLong(isi, "txn_mcc_code"));
                refundPoin.setTxnCode(getValueLong(isi, "txn_code"));
                refundPoin.setTxnAuthCode(getValueString(isi, "txn_auth_code"));
                refundPoin.setTxnAmount(getValueDouble(isi, "txn_amt"));
                refundPoin.setAccountPermCrLimit(getValueDouble(isi, "crdacct_perm_crlimit"));
                refundPoin.setDoubleLimit(getValueDouble(isi, "double_limit"));
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
        List<Map<String, Object>> trm001 = jdbcQueryService.executeCustomQuery(customQuery.TRM001());
        List<AmlaRuleResult> trm001Amla = this.postToMenyimpangA(trm001, "TRM001", localDate);
        List<Map<String, Object>> trm007 = jdbcQueryService.executeCustomQuery(customQuery.TRM007());
        List<AmlaRuleResult> trm007Amla = this.postToMenyimpangB(trm007, "TRM007", localDate);
        List<Map<String, Object>> trm002 = jdbcQueryService.executeCustomQuery(customQuery.TRM002());
        List<AmlaRuleResult> trm002Amla = this.postToRefundPoin(trm002, "TRM002", localDate);
        List<Map<String, Object>> trm003 = jdbcQueryService.executeCustomQuery(customQuery.TRM003());
        List<AmlaRuleResult> trm003Amla = this.postToRefundPoin(trm003, "TRM003", localDate);
        List<Map<String, Object>> trm006 = jdbcQueryService.executeCustomQuery(customQuery.TRM006());
        List<AmlaRuleResult> trm006Amla = this.postToRefundPoin(trm006, "TRM006", localDate);
        List<Map<String, Object>> trm004 = jdbcQueryService.executeCustomQuery(customQuery.TRM004());
        List<AmlaRuleResult> trm004Amla = this.postToMerchantMenyimpangA(trm004, "TRM004", localDate);
        List<Map<String, Object>> trm005 = jdbcQueryService.executeCustomQuery(customQuery.TRM005());
        List<AmlaRuleResult> trm005Amla = this.postToMerchantMenyimpangB(trm005, "TRM005", localDate);
        // System.out.println(trm004Amla.size());
        // System.out.println(trm005Amla.size());
        // System.out.println(trm006Amla.size());
        hasil.addAll(trm001Amla);
        hasil.addAll(trm007Amla);
        hasil.addAll(trm002Amla);
        hasil.addAll(trm003Amla);
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
                merchMenyimpangB.setMcc(getValueLong(isi, "mcc"));
                merchMenyimpangB.setFrequency(getValueLong(isi, "freq"));
                merchMenyimpangB.setBankNumber(getValueLong(isi, "txnd_bank_nbr"));
                merchMenyimpangB.setJulianDate(getValueLong(isi, "julian_mis_date"));
                merchMenyimpangB.setOwnerMemo(getValueString(isi, "mbpf_owner1_memo"));
                merchMenyimpangB.setTriggeredRule(string);
                merchMenyimpangB.setMerchLocalName(getValueString(isi, "mech_lcl_name"));
                merchMenyimpangB.setMerchNumber(getValueLong(isi, "txnd_mech_nbr"));
                merchMenyimpangB.setTotalTrx(getValueDouble(isi, "tot_trx"));
                merchMenyimpangB.setTxnPostDate(getValueLong(isi, "txnd_post_date"));
                merchMenyimpangB.setCardNumber(getValueString(isi, "txnd_card_nbr"));
                merchMenyimpangB
                        .setAccountNumber(getValueString(isi, "crdacct_nbr"));
                merchMenyimpangB.setCreditLimit(getValueDouble(isi, "crdacct_perm_crlimit"));
                merchMenyimpangB.setCustLocalName(getValueString(isi, "cust_local_name"));
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
                merchMenyimpangA.setAvg200PerMonth(getValueDouble(isi, "avg_per_mth_200"));
                merchMenyimpangA.setAvgPerMonth(getValueDouble(isi, "avg_per_mth"));
                merchMenyimpangA.setBankNumber(getValueLong(isi, "txnh_bank_nbr"));
                merchMenyimpangA.setJulianDate(getValueLong(isi, "julian_mis_date"));
                merchMenyimpangA.setOwnerMemo(getValueString(isi, "mbpf_owner1_memo"));
                merchMenyimpangA.setTriggeredRule(string);
                merchMenyimpangA.setMerchLocalName(getValueString(isi, "mech_lcl_name"));
                merchMenyimpangA.setMerchNumber(getValueLong(isi, "txnh_mech_nbr"));
                merchMenyimpangA.setTxnAmount(getValueDouble(isi, "txnh_txn_amt"));
                merchMenyimpangA.setTxnPostDate(getValueLong(isi, "txnh_post_date"));
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

    // private List<AmlaRuleResult> postTo
    private List<AmlaRuleResult> postToMenyimpangA(List<Map<String, Object>> trm001, String string,
            LocalDate localDate) {
        List<MenyimpangA> indiA = new ArrayList<>();
        List<AmlaRuleResult> hasil = new ArrayList<>();
        String julianDate = localDate.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        if (this.menyimpangA_Repository.findByJulianDate(Long.valueOf(julianDate)).isEmpty()) {
            for (Map isi : trm001) {
                String randUUID = UUID.randomUUID().toString();
                MenyimpangA menyimpangA = new MenyimpangA();
                menyimpangA.setId(randUUID);
                menyimpangA.setJulianDate(getValueLong(isi, "julian_mis_date"));
                menyimpangA.setBankNumber(getValueLong(isi, "olst_txn_bank_nbr"));
                menyimpangA.setAccNumber(getValueString(isi, "olst_txn_acct_nbr"));
                menyimpangA.setCustLocalName(getValueString(isi, "cust_local_name"));
                menyimpangA.setCustEmpName(getValueString(isi, "cust_emp_name"));
                menyimpangA.setTotalTrx(getValueDouble(isi, "tot_trx"));
                menyimpangA.setAccountBlockCode(getValueString(isi, "crdacct_blk_code"));
                menyimpangA.setAccountPermCrLimit(getValueDouble(isi, "crdacct_perm_crlimit"));
                menyimpangA.setMaxLimit(getValueDouble(isi, "max_limit"));
                menyimpangA.setCustGoccCode(getValueString(isi, "cust_gocc_code"));
                menyimpangA.setOldStmDate(getValueLong(isi, "old_stm_date"));
                menyimpangA.setTriggeredRule(string);
                menyimpangA.setCreatedAt(LocalDate.now());
                indiA.add(menyimpangA);
            }
            this.menyimpangA_Repository.saveAll(indiA);
        }
        indiA = this.menyimpangA_Repository.findByJulianDate(Long.valueOf(julianDate));
        for (MenyimpangA isix : indiA) {
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

    private List<AmlaRuleResult> postToMenyimpangB(List<Map<String, Object>> trm007, String string,
            LocalDate localDate) {
        List<MenyimpangB> indiB = new ArrayList<>();
        List<AmlaRuleResult> hasil = new ArrayList<>();
        String julianDate = localDate.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        if (this.menyimpangB_Repository.findByJulianDate(Long.valueOf(julianDate)).isEmpty()) {
            for (Map isi : trm007) {
                String randUUID = UUID.randomUUID().toString();
                MenyimpangB menyimpangB = new MenyimpangB();
                menyimpangB.setId(randUUID);
                menyimpangB.setJulianDate(getValueLong(isi, "julian_mis_date"));
                menyimpangB.setBankNumber(getValueLong(isi, "olst_txn_bank_nbr"));
                menyimpangB.setAccNumber(getValueString(isi, "olst_txn_acct_nbr"));
                menyimpangB.setCustLocalName(getValueString(isi, "cust_local_name"));
                menyimpangB.setCustEmpName(getValueString(isi, "cust_emp_name"));
                menyimpangB.setTotalTrx(getValueDouble(isi, "tot_trx"));
                menyimpangB.setAccountBlockCode(getValueString(isi, "crdacct_blk_code"));
                menyimpangB.setAccountPermCrLimit(getValueDouble(isi, "crdacct_perm_crlimit"));
                menyimpangB.setMaxLimit(getValueDouble(isi, "max_limit"));
                menyimpangB.setCustGoccCode(getValueString(isi, "cust_gocc_code"));
                menyimpangB.setOldStmDate(getValueLong(isi, "old_stm_date"));
                menyimpangB.setTriggeredRule(string);
                menyimpangB.setCreatedAt(LocalDate.now());
                indiB.add(menyimpangB);
            }
            this.menyimpangB_Repository.saveAll(indiB);
        }
        indiB = this.menyimpangB_Repository.findByJulianDate(Long.valueOf(julianDate));
        for (MenyimpangB isix : indiB) {
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

    private static String getValueString(Map<String, Object> map, String key) {
        // Use the get method and check for null
        try {
            String hasil = map.get(key).toString();
            return hasil;
        } catch (Exception e) {
            return null;
        }
    }

    private static Long getValueLong(Map<String, Object> map, String key) {
        // Use the get method and check for null
        try {
            Long hasil = Long.valueOf(map.get(key).toString());
            return hasil;
        } catch (Exception e) {
            return null;
        }
    }

    private static Double getValueDouble(Map<String, Object> map, String key) {
        // Use the get method and check for null
        try {
            Double hasil = Double.valueOf(map.get(key).toString());
            return hasil;
        } catch (Exception e) {
            return null;
        }
    }

}
