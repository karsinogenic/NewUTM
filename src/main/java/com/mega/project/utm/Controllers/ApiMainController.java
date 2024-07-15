package com.mega.project.utm.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.JulianFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// import org.elasticsearch.index.query.BoolQueryBuilder;
// import org.elasticsearch.index.query.QueryBuilder;
// import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mega.project.utm.Models.HistoryMemo;
import com.mega.project.utm.Models.HistoryMerchTrans;
import com.mega.project.utm.Models.Merchant;
import com.mega.project.utm.Models.QR002;
import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Models.TempHistoryMerchTrans;
import com.mega.project.utm.Models.AMLA.AmlaMerchantRuleResult;
import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import com.mega.project.utm.Models.AMLA.CombinedAmlaMerchant;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangA;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangB;
import com.mega.project.utm.Models.AMLA.RefundPoinC;
import com.mega.project.utm.Repositories.AmlaMerchantRuleResultRepository;
import com.mega.project.utm.Repositories.AmlaRuleResultRepository;
import com.mega.project.utm.Repositories.HistoryMerchTransRepository;
import com.mega.project.utm.Repositories.MenyimpangA_Repository;
import com.mega.project.utm.Repositories.MenyimpangB_Repository;
import com.mega.project.utm.Repositories.MerchMenyimpangA_Repository;
import com.mega.project.utm.Repositories.MerchMenyimpangB_Repository;
import com.mega.project.utm.Repositories.MerchantRepository;
import com.mega.project.utm.Repositories.RefundPoinRepository;
import com.mega.project.utm.Repositories.RuleResultRepository;
import com.mega.project.utm.Repositories.SaldoKreditRepository;
import com.mega.project.utm.services.AESEncryptDecrypt;
import com.mega.project.utm.services.CustomQuery;
import com.mega.project.utm.services.CustomRule;
import com.mega.project.utm.services.ElasticService;
import com.mega.project.utm.services.ExcelWriteRead;
import com.mega.project.utm.services.JdbcQueryService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/")
public class ApiMainController {

    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private HistoryMerchTransRepository historyMerchTransRepository;
    @Autowired
    private RefundPoinRepository refundPoinC_Repository;
    @Autowired
    private CustomRule customRule;
    @Autowired
    private JdbcQueryService jdbcQueryService;
    @Autowired
    private MerchMenyimpangA_Repository menyimpangA_Repository;
    @Autowired
    private MerchMenyimpangB_Repository menyimpangB_Repository;
    @Autowired
    private AmlaRuleResultRepository amlaRuleResultRepository;
    @Autowired
    private AmlaMerchantRuleResultRepository amlaMerchantRuleResultRepository;
    @Autowired
    private RuleResultRepository ruleResultRepository;
    @Autowired
    private MenyimpangA_Repository menyimpangA_Repository2;
    @Autowired
    private MenyimpangB_Repository menyimpangB_Repository2;

    private JulianFields julianFields;

    @Autowired
    private SaldoKreditRepository saldoKreditRepository;

    @GetMapping("getExcel")
    public List<AmlaMerchantRuleResult> findAllAmlaMerchant() {
        List<AmlaMerchantRuleResult> amlaMerchantRuleResults = this.amlaMerchantRuleResultRepository.findAll();
        ExcelWriteRead excelWriteRead = new ExcelWriteRead();
        excelWriteRead.writeXLSAmlaMerchant(amlaMerchantRuleResults, "coba.xls");
        return amlaMerchantRuleResults;
    }

    @GetMapping("test")
    public List<CombinedAmlaMerchant> test() {
        List<CombinedAmlaMerchant> list = new ArrayList<>();
        List<String> listAmlaId = this.amlaMerchantRuleResultRepository.findAmlaIdByIsSentAndIsApprovedIsNull(false);
        JSONArray array = new JSONArray(listAmlaId);
        String strArray = array.toString().replace("\"", "'").replace("[", "(").replace("]", ")");
        System.out.println(array);
        List<AmlaMerchantRuleResult> listAmla = this.amlaMerchantRuleResultRepository
                .findByIsSentAndIsApprovedIsNull(false);
        List<MerchMenyimpangA> merchMenyimpangA = this.menyimpangA_Repository.findByListId(listAmlaId);
        List<MerchMenyimpangB> merchMenyimpangB = this.menyimpangB_Repository.findByListId(listAmlaId);

        // List<Map<String, Object>> hasil = jdbcQueryService.executeCustomQuery(query);
        return list;
    }

    @GetMapping("ip")
    public String bobo(HttpServletRequest request) {
        String client = request.getRemoteAddr();
        System.out.println(client);
        System.out.println(request.getLocalAddr());
        System.out.println(request.getServerName());
        return client;
    }

    // @GetMapping("getQuery")
    // public String getMethodName() {
    // ElasticService elasticService = new ElasticService();
    // BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
    // boolQueryBuilder.must(elasticService.matchQuery("CARD_SUP_REL", "P"));
    // boolQueryBuilder.must(elasticService.matchQuery("CRDACCT_NBR",
    // "0000000000015511554"));
    // JSONObject boolJsonObject = new JSONObject(boolQueryBuilder.toString());
    // JSONObject query = new JSONObject();
    // query.put("query", boolJsonObject);
    // System.out.println(query);
    // return query.toString();
    // }

    @GetMapping("testAmla")
    public List<Map<String, Object>> executeAmla() {
        CustomQuery customQuery = new CustomQuery(LocalDate.of(2023, 11, 10));
        System.out.println(customQuery.TRM002());
        System.out.println(this.customRule.executeAmla(customQuery.TRM002()).size());
        return this.customRule.executeAmla(customQuery.TRM002());
    }

    // @GetMapping("testAmla1")
    // public void newAmla() {
    // CustomRule customRule = new CustomRule(jdbcQueryService,
    // historyMerchTransRepository, null, null, null,
    // refundPoinC_Repository);
    // customRule.setMerchMenyimpangA_Repository(menyimpangA_Repository);
    // customRule.setMerchMenyimpangB_Repository(menyimpangB_Repository);

    // customRule.allAmla(LocalDate.of(2023, 11, 8));
    // }

    // @GetMapping("testAmla2")
    // public List<AmlaRuleResult> newAmla2() {
    // List<AmlaRuleResult> hasil = new ArrayList<>();
    // CustomRule customRule = new CustomRule(jdbcQueryService,
    // historyMerchTransRepository, null, null, null,
    // refundPoinC_Repository);
    // customRule.setMerchMenyimpangA_Repository(menyimpangA_Repository);
    // customRule.setMerchMenyimpangB_Repository(menyimpangB_Repository);
    // customRule.setAmlaRuleResultRepository(amlaRuleResultRepository);

    // hasil = customRule.allAmla(LocalDate.of(2023, 11, 8));
    // return hasil;
    // }

    @PostMapping("findAllAmla")
    public List<AmlaRuleResult> newAmla3(@RequestBody Map isi) {
        List<AmlaRuleResult> hasil = new ArrayList<>();
        CustomRule customRule = new CustomRule(jdbcQueryService, historyMerchTransRepository, null, null, null,
                refundPoinC_Repository, menyimpangA_Repository2, menyimpangB_Repository2,
                amlaMerchantRuleResultRepository, saldoKreditRepository);
        customRule.setMerchMenyimpangA_Repository(menyimpangA_Repository);
        customRule.setMerchMenyimpangB_Repository(menyimpangB_Repository);
        customRule.setAmlaRuleResultRepository(amlaRuleResultRepository);
        if (isi.get("julian") != null) {

            hasil = customRule.allAmla(convertToGregorianDate(isi.get("julian").toString()));
        } else if (isi.get("date") != null) {
            hasil = customRule.allAmla(LocalDate.parse(isi.get("date").toString()));
        } else {
            hasil = customRule.allAmla(LocalDate.now().minusDays(1));
        }
        return hasil;
    }

    @PostMapping("findAllQR")
    public List<RuleResult> findAllQR(@RequestBody Map isi) {
        List<RuleResult> hasil = new ArrayList<>();
        CustomRule customRule = new CustomRule(jdbcQueryService, historyMerchTransRepository, null, null,
                this.ruleResultRepository,
                refundPoinC_Repository, menyimpangA_Repository2, menyimpangB_Repository2,
                amlaMerchantRuleResultRepository, saldoKreditRepository);
        customRule.setMerchMenyimpangA_Repository(menyimpangA_Repository);
        customRule.setMerchMenyimpangB_Repository(menyimpangB_Repository);
        customRule.setAmlaRuleResultRepository(amlaRuleResultRepository);
        if (isi.get("julian") != null) {
            hasil = customRule.allRule(convertToGregorianDate(isi.get("julian").toString()));
        } else if (isi.get("date") != null) {
            hasil = customRule.allRule(LocalDate.parse(isi.get("date").toString()));
        } else {
            hasil = customRule.allRule(LocalDate.now().minusDays(1));
        }
        return hasil;
    }

    private static LocalDate convertToGregorianDate(String julianDateString) {
        System.out.println(julianDateString);
        // Parse the year and day components from the Julian date string
        int year = Integer.parseInt(julianDateString.substring(0, 4));
        int dayOfYear = Integer.parseInt(julianDateString.substring(4));

        // Convert to LocalDate
        LocalDate baseDate = LocalDate.of(year, 1, 1);
        LocalDate gregorianDate = baseDate.plusDays(dayOfYear - 1);
        System.out.println(gregorianDate);

        return gregorianDate;
    }

    @GetMapping("testJulian")
    public void julianDate() {
        LocalDate ld = LocalDate.of(2023, 11, 8);
        Long lDate = JulianFields.JULIAN_DAY.getFrom(ld);
        String newDate = ld.format(DateTimeFormatter.ISO_ORDINAL_DATE);

        // LocalDate coba = ordinalToGregorian(Integer.valueOf(newDate.substring(0, 4)),
        // Integer.valueOf(newDate.substring(4, newDate.length())));

        // System.out.println(convertToGregorianDate("23314"));
        System.out.println(lDate);
        System.out.println(newDate);
        // System.out.println(coba);
    }

    // @GetMapping("test2")
    // public List<TempHistoryMerchTrans> test2() throws Exception {
    // List<TempHistoryMerchTrans> list2 =
    // customRule.DetailQR003(LocalDate.parse("2023-10-20"));
    // return list2;
    // }
}
