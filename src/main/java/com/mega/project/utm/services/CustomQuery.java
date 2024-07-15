package com.mega.project.utm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomQuery {

    private LocalDate ldt;

    @Autowired
    private JdbcQueryService jdbcQueryService;

    public CustomQuery(LocalDate ldt) {
        this.ldt = ldt;
    }

    public String myQuery(String kode) {
        String query = "";
        if (kode.equals("QR001")) {
            query = QR001();
        }
        if (kode.equals("QR002")) {
            query = QR002();
        }
        if (kode.equals("QR003")) {
            query = QR003();
        }
        if (kode.equals("QR006")) {
            query = QR006();
        }
        return query;
    }

    public String QR001() {
        String query = """
                select t1."Merchant_Id" ,t1."Card_Number",count(*) as count,t2."MBPF_OWNER2_NAME2" as owner,sum(t1."Amount") as sum from "HISTORY_MSP_QR_CODE_DATA_HISTORY" t1 right JOIN "MECH_DATA_PROFILE" t2 ON t1."Merchant_Id" = LPAD(t2."MECH_NBR", 11, '0') where t1."Payment_Date" = '
                """
                + ldt.toString()
                + """
                        ' and t1."Amount" > 100000 and t1."Card_Number" is not null and t2."MBPF_OWNER2_NAME2" NOT LIKE 'GROUP%' group by t1."Merchant_Id",t1."Card_Number",t2."MBPF_OWNER2_NAME2" having count(*) > 1;
                        """;
        return query;
    }

    public String QR002() {
        String query = """
                select t1."Merchant_Id" ,t1."Card_Number",count(*) as count,t2."MBPF_OWNER2_NAME2" as owner,sum(t1."Amount") as sum from "HISTORY_MSP_QR_CODE_DATA_HISTORY" t1 right JOIN "MECH_DATA_PROFILE" t2 ON t1."Merchant_Id" = LPAD(t2."MECH_NBR", 11, '0') where t1."Payment_Date" = '
                """
                + ldt.toString()
                + """
                        ' and t1."Card_Number" is not null and t2."MBPF_OWNER2_NAME2" NOT LIKE 'GROUP%' group by t1."Merchant_Id",t1."Card_Number",t2."MBPF_OWNER2_NAME2" having count(*)  > 10;
                        """;
        return query;
    }

    public String QR003() {
        LocalDate lDate1 = ldt.minusMonths(6);
        LocalDate lDate2 = ldt.minusDays(7);

        String query = """
                select t1."Merchant_Id",SUM(t1."Amount") as sum,count(*) as count,t2."MBPF_OWNER2_NAME2" as owner FROM "HISTORY_MSP_QR_CODE_DATA_HISTORY" t1 right JOIN "MECH_DATA_PROFILE" t2 ON t1."Merchant_Id" = LPAD(t2."MECH_NBR", 11, '0') where t1."Payment_Date" >
                    '"""
                + lDate2.toString() + "' and t2.\"MECH_OPEN_DATE\" > " + lDate1.toString().replace("-", "")
                + """
                         and t2."MBPF_OWNER2_NAME2" NOT LIKE 'GROUP%' group by t1."Merchant_Id",t2."MBPF_OWNER2_NAME2" having SUM(t1."Amount") > 1000000
                        """;
        // having SUM(t1."Amount") > 20000000
        return query;
    }

    public String QR006() {
        String query = """
                select "Merchant_Id" ,count(*) as count,sum("Amount") as sum,t2."MBPF_OWNER2_NAME2" as owner from "HISTORY_MSP_QR_CODE_DATA_HISTORY" where "Payment_Date" = '
                """
                + ldt.toString() + """
                        ' group by "Merchant_Id" having count(*)  > 10 and sum("Amount") > 25000;
                        """;
        return query;
    }

    public String TRM002() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_refund_poin_a" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        return query;
    }

    public String TRM003() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_refund_poin_b" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        return query;
    }

    public String TRM006() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_refund_poin_c" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        return query;
    }

    public String TRM004() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_merchant_menyimpang_a" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        System.out.println("julian date: " + julianDate);
        return query;
    }

    public String TRM005() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_merchant_menyimpang_b" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        return query;
    }

    public String TRM001() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_menyimpang" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        return query;
    }

    public String TRM007() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_menyimpang_b" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        return query;
    }

    public String SK1() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_saldo_kredit_a" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        return query;
    }

    public String SK2() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_saldo_kredit_b" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        return query;
    }

    public String SK3() {
        String julianDate = ldt.format(DateTimeFormatter.ISO_ORDINAL_DATE).replace("-", "");
        String query = """
                select * from "cronos_saldo_kredit_c" where "julian_mis_date" ="""
                + julianDate + """
                        ;
                        """;
        return query;
    }

}
