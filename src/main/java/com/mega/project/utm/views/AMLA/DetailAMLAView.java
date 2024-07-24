package com.mega.project.utm.views.AMLA;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mega.project.utm.Models.AllData;
import com.mega.project.utm.Models.HistoryMemo;
import com.mega.project.utm.Models.MapAmlaDetail;
import com.mega.project.utm.Models.MapAmlaSummary;
import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import com.mega.project.utm.Models.AMLA.CronosMenyimpangBDetailTrx6m;
import com.mega.project.utm.Models.AMLA.CronosSaldoKreditADetailTrx6m;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangA;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangB;
import com.mega.project.utm.Models.AMLA.RefundPoin;
import com.mega.project.utm.Models.util.ResultPaymentRetail;
import com.mega.project.utm.Repositories.AmlaRuleResultRepository;
import com.mega.project.utm.Repositories.HistoryMemoRepository;
import com.mega.project.utm.services.JdbcQueryService;
import com.mega.project.utm.services.RetailPaymentService;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.AmlaSummary;
import com.mega.project.utm.views.Components.MyNotification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.Query;

@PermitAll
@PageTitle("AMLA Detail")
@Route(value = "amla/detail", layout = MainLayout.class)
public class DetailAMLAView extends VerticalLayout implements HasUrlParameter<String> {

    private JdbcQueryService jdbcQueryService;
    private AmlaRuleResultRepository amlaRuleResultRepository;
    private HistoryMemoRepository historyMemoRepository;
    private MyNotification myNotification;

    FormLayout formLayout = new FormLayout();

    GridCrud<HistoryMemo> crudMemo = new GridCrud<>(HistoryMemo.class);
    ComboBox<String> comboBox = new ComboBox<>("Status");
    TextArea input = new TextArea();
    Button memoBtn = new Button();
    TextField memoField = new TextField();
    TextField statusField = new TextField();
    TextField reviewerField = new TextField();
    TextField approverField = new TextField();
    Notification notif = new Notification();

    String tableHist = null;
    AmlaRuleResult ruleResultHist = null;

    private static boolean containsWord(String key, String[] wordsArray) {
        for (String word : wordsArray) {
            if (key.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        ObjectMapper objectMapper = new ObjectMapper();
        String param = new String(Base64.getDecoder().decode(parameter));
        String[] param1 = param.split("_");

        String role = VaadinSession.getCurrent().getAttribute("role").toString();

        String query = "select * from \"";

        if (param1[0].contains("SK")) {
            query += "SALDO_KREDIT\" ";
            if (param1[0].equals("SK1")) {
                tableHist = "cronos_saldo_kredit_a_detail_trx_6m";
            }
            if (param1[0].equals("SK2")) {
                tableHist = "cronos_saldo_kredit_b_detail_trx_6m";
            }
            if (param1[0].equals("SK3")) {
                tableHist = "cronos_saldo_kredit_c_detail_trx_6m";
            }

        } else if (param1[0].equals("TRM005")) {
            query += "MERCH_MENYIMPANG_B\" ";
            tableHist = "cronos_merchant_menyimpang_b_detail_trx_6m";

        } else if (param1[0].equals("TRM004")) {
            query += "MERCH_MENYIMPANG_A\" ";
            tableHist = "cronos_merchant_menyimpang_a_detail_trx_6m";
        } else if (param1[0].equals("TRM007")) {
            query += "MENYIMPANG_B\" ";
            tableHist = "cronos_menyimpang_b_detail_trx_6m";

        } else if (param1[0].equals("TRM001")) {
            query += "MENYIMPANG_A\" ";
            tableHist = "cronos_menyimpang_detail_trx_6m";

        } else {
            query += "REFUND_POIN\" ";
            if (param1[0].equals("TRM006")) {
                tableHist = "cronos_refund_poin_c_detail_trx_6m";
            }
            if (param1[0].equals("TRM003")) {
                tableHist = "cronos_refund_poin_b_detail_trx_6m";
            }
            if (param1[0].equals("TRM002")) {
                tableHist = "cronos_refund_poin_a_detail_trx_6m";
            }
        }

        query += "where \"id\" = '" + param1[1] + "'";

        AmlaRuleResult ruleResult = this.amlaRuleResultRepository.findById(param1[2]).get();
        ruleResultHist = ruleResult;
        VerticalLayout dialogLayout = createDialogLayout(null);

        if (ruleResult.getMemo() != null) {
            memoField.setValue(ruleResult.getMemo());
            reviewerField.setValue(ruleResult.getReviewBy() == null ? "" : ruleResult.getReviewBy());
            statusField.setValue(ruleResult.getStatus() == null ? "" : ruleResult.getStatus());
            approverField.setValue(ruleResult.getApprovedBy() == null ? "" : ruleResult.getApprovedBy());
            dialogLayout = createDialogLayout(ruleResult.getMemo());
        }

        List<HistoryMemo> latestMemos = this.historyMemoRepository.findByRuleId(param1[2]);
        crudMemo.setFindAllOperation(() -> latestMemos);
        HistoryMemo latestMemo = new HistoryMemo();
        if (!latestMemos.isEmpty()) {
            latestMemos.get(latestMemos.size() - 1);
        }

        List<Map<String, Object>> hasil = jdbcQueryService.executeCustomQuery(query);
        JSONObject jsonData = new JSONObject(hasil.get(0));

        jsonData.remove("id");
        jsonData.remove("createdAt");
        Iterator<String> iterator = jsonData.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            TextField textField = new TextField(key);
            String value="";
            try {
                value = jsonData.get(key).toString();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (key.contains("julianDate")) {
                textField = new TextField("Date");
                LocalDate localDate = ordinalToGregorian(Integer.valueOf(value.substring(0, 4)),
                        Integer.valueOf(value.substring(4, value.length())));
                value = localDate.toString();
            }

            try {
                String doubleKey[] = { "avg", "trx", "Amount", "total", "creditLimit",
                        "limit", "txn", "amt" };
                if (containsWord(key, doubleKey)) {
                    double doubleValue = Double.parseDouble(value);
                    // long longValue = (long) doubleValue;
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                    value = numberFormat.format(doubleValue).toString();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

            textField.setValue(value);
            formLayout.add(textField);
        }

        // crud.setFindAllOperation();
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'setParameter'");
        memoBtn = new Button("Tambah Memo");

        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Tambah Memo");

        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog, ruleResult);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        dialog.setWidth("50%");

        memoBtn.addClickListener(e -> dialog.open());
        memoBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout memoApproval = new HorizontalLayout();
        Button approvalBtn = new Button("Approve");
        approvalBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        approvalBtn.addClickListener(event1 -> {
            String approvedBy = (VaadinSession.getCurrent().getAttribute("username").toString());

            ruleResult.setApprovedBy(approvedBy);
            ruleResult.setIsApproved(true);
            ruleResult.setIsSent(false);
            ruleResult.setLockedBy(null);

            latestMemo.setApprovedBy(approvedBy);
            latestMemo.setApprovalStatus(true);
            try {
                amlaRuleResultRepository.save(ruleResult);
                historyMemoRepository.save(latestMemo);

                notif = this.myNotification.success("Berhasil approve");
                UI.getCurrent().getPage().getHistory().back();

            } catch (Exception e) {
                notif = this.myNotification.error("Gagal approve");
                UI.getCurrent().getPage().reload();

                // TODO: handle exception
            }
            notif.open();

            // System.out.println("approve");
        });

        Button refusalBtn = new Button("Denied");
        refusalBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        refusalBtn.addClickListener(event2 -> {
            String approvedBy = (VaadinSession.getCurrent().getAttribute("username").toString());
            ruleResult.setApprovedBy(approvedBy);
            ruleResult.setIsApproved(false);
            ruleResult.setIsSent(false);
            ruleResult.setLockedBy(null);
            latestMemo.setApprovedBy(approvedBy);
            latestMemo.setApprovalStatus(false);
            try {
                amlaRuleResultRepository.save(ruleResult);
                historyMemoRepository.save(latestMemo);
                notif = this.myNotification.success("Berhasil denied");
                UI.getCurrent().getPage().getHistory().back();

            } catch (Exception e) {
                notif = this.myNotification.error("Gagal denied");
                UI.getCurrent().getPage().reload();

                // TODO: handle exception
            }
            notif.open();

            // System.out.println("denied");
        });

        memoApproval.add(approvalBtn, refusalBtn);
        if (role.contains("ANALYST") || role.contains("USER")) {
            memoApproval.setVisible(false);
        } else {
            memoApproval.setVisible((ruleResult.getIsApproved() != null || latestMemos.isEmpty()) ? false : true);
        }

        HorizontalLayout memoCombo = new HorizontalLayout();
        Label statusLabel = new Label("Latest Status");
        Label reviewerLabel = new Label("Latest Reviewer");
        Label approverLabel = new Label("Latest Approver");
        Label memoLabel = new Label("Latest Memo:");

        memoCombo.add(memoLabel, memoField, statusLabel, statusField, reviewerLabel, reviewerField, approverLabel,
                approverField);
        memoCombo.setAlignItems(FlexComponent.Alignment.CENTER);

        // status approval badge
        Span pending1 = new Span(createIcon(VaadinIcon.CLOCK),
                new Span("Pending"));
        pending1.getElement().getThemeList().add("badge");

        Span pending2 = new Span(createIcon(VaadinIcon.BOOK),
                new Span("No Memo"));
        pending2.getElement().getThemeList().add("badge");

        Span confirmed1 = new Span(createIcon(VaadinIcon.CHECK),
                new Span("Approved"));
        confirmed1.getElement().getThemeList().add("badge success");

        Span denied1 = new Span(createIcon(VaadinIcon.EXCLAMATION_CIRCLE_O),
                new Span("Denied"));
        denied1.getElement().getThemeList().add("badge error");
        if (ruleResult.getIsApproved() == null && !latestMemos.isEmpty()) {
            memoCombo.add(pending1);
        } else if (ruleResult.getIsApproved() == null && latestMemos.isEmpty()) {
            memoCombo.add(pending2);
        } else if (ruleResult.getIsApproved() == true) {
            memoCombo.add(confirmed1);
        } else {
            memoCombo.add(denied1);
        }
        //

        // memoCombo.add(memoApproval);

        memoCombo.setWidth("100%");
        memoField.setWidth("20vw");

        crudMemo.getCrudLayout().addToolbarComponent(memoBtn);
        // crudMemo.getCrudLayout().addToolbarComponent(memoApproval);
        crudMemo.setAddOperationVisible(false);
        crudMemo.setUpdateOperationVisible(false);
        crudMemo.setDeleteOperationVisible(false);
        crudMemo.getGrid().setColumns("memo", "reviewBy", "approvedBy", "status");

        H1 title = new H1("Memo History");
        Div memoDiv = new Div(title, crudMemo);
        memoDiv.setWidth("100%");

        // Grid<MapAmlaDetail> gridAmla = makeGrid("2024-03-14", "0000000000011021397",
        // "cronos_menyimpang_b_detail_trx_6m", "create_date", "olst_txn_acct_nbr");
        // Grid<MapAmlaSummary> gridAmlaSummary = makeGridSummary("2024-03-14",
        // "0000000000011021397",
        // "cronos_menyimpang_b_detail_trx_6m", "create_date", "olst_txn_acct_nbr");

        Button txn_history = new Button("Transaction History");
        txn_history.addClickListener(e -> makeDialogTxnHistory(ruleResultHist).open());
        Button txn_summary = new Button("Transaction Summary");
        txn_summary.addClickListener(e -> makeDialogTxnSummary(ruleResultHist).open());
        HorizontalLayout txn_btn_combo = new HorizontalLayout(memoApproval, txn_history, txn_summary);

        // memoDiv.addClassName(Vaadin);
        add(memoCombo, txn_btn_combo, formLayout, memoDiv);
    }

    private Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }

    private RetailPaymentService rps;

    public DetailAMLAView(JdbcQueryService jdbcQueryService, AmlaRuleResultRepository amlaRuleResultRepository,
            MyNotification myNotification, HistoryMemoRepository historyMemoRepository, RetailPaymentService rps) {
        this.jdbcQueryService = jdbcQueryService;
        this.amlaRuleResultRepository = amlaRuleResultRepository;
        this.myNotification = myNotification;
        this.historyMemoRepository = historyMemoRepository;
        this.rps = rps;

    }

    public LocalDate ordinalToGregorian(int year, int ordinalDate) {
        // System.out.println("year: " + year);
        // System.out.println("ordinal: " + ordinalDate);
        // Create a LocalDate object for January 1st of the given year
        LocalDate baseDate = LocalDate.of(year, 1, 1);

        // Use the plusDays method to add the ordinal date - 1
        LocalDate resultDate = baseDate.plusDays(ordinalDate - 1);

        return resultDate;
    }

    private Button createSaveButton(Dialog dialog, AmlaRuleResult ruleResult) {
        Button save = new Button("Save");
        HistoryMemo historyMemo = new HistoryMemo();
        save.addClickListener(e -> {
            ruleResult.setReviewBy(ruleResult.getLockedBy());
            historyMemo.setReviewBy(ruleResult.getLockedBy());
            historyMemo.setMemo(input.getValue());
            historyMemo.setRuleId(ruleResult.getId());
            historyMemo.setReviewDateTime(LocalDateTime.now());
            historyMemo.setStatus(comboBox.getValue());
            historyMemo.setRule(ruleResult.getTriggeredRule());

            Notification notif = new Notification();
            try {
                ruleResult.setActionDate(LocalDate.now());
                ruleResult.setMemo(input.getValue());
                ruleResult.setStatus(comboBox.getValue());
                ruleResult.setApprovedBy(null);
                ruleResult.setIsApproved(null);
                ruleResult.setIsSent(true);
                this.amlaRuleResultRepository.save(ruleResult);
                this.historyMemoRepository.save(historyMemo);
                notif = this.myNotification.success("Berhasil menambahkan memo");
                notif.open();
                dialog.close();
                UI.getCurrent().getPage().getHistory().back();
                ;
            } catch (Exception ez) {
                notif = this.myNotification.error("Gagal menambahkan memo");
                notif.open();
                dialog.close();
            }
        });
        return save;
    }

    private VerticalLayout createDialogLayout(String oldMemo) {
        comboBox.setItems("Wajar", "Tidak Wajar");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("100%");
        input.setWidth("100%");
        comboBox.setWidth("100%");
        input.setLabel("Memo");
        // if (oldMemo != null) {
        // input.setValue(oldMemo);
        // }
        verticalLayout.add(input, comboBox);
        return verticalLayout;
    }

    private List<Map<String, Object>> getAllData(String date, String accnum, String table, String colDate,
            String colAccNum) {

        // and cm.olst_txn_amt not like 9999
        if (table == null) {
            notif = this.myNotification.error("Table Not Exist");
            notif.open();
            return null;
        }

        String queryString = String.format(
                "select * from %s cm where Date(cm.%s)= Date('%s') and cm.%s = '%s'",
                table,
                colDate, date, colAccNum, accnum);
        System.out.println(queryString);

        List<Map<String, Object>> hasilQuery = new ArrayList<>();
        try {
            hasilQuery = jdbcQueryService
                    .executeCustomQuery(queryString + " and cm.olst_txn_card_nbr not like '9999%'");
        } catch (Exception e) {

        }
        if (hasilQuery.size() == 0) {
            notif = this.myNotification.warning("Data not found");
            notif.open();
        }
        return hasilQuery;

        // Query query = entityManager.createNativeQuery(queryString, kelas);
        // List<?> yow = new ArrayList<>();
        // System.out.println("kelas1: " + kelas.getName());
        // if (kelas.getName().contains("CronosMenyimpangBDetail")) {
        // yow = (List<CronosMenyimpangBDetailTrx6m>) query.getResultList();
        // } else if (kelas.getName().contains("CronosSaldoKreditADetail")) {
        // yow = (List<CronosSaldoKreditADetailTrx6m>) query.getResultList();
        // }
        // // System.out.println("kelas: " + yow.get(0).getClass());
        // JSONArray object = new JSONArray(yow);
        // return yow;
    }

    private List<MapAmlaDetail> mapAllDataDetail(List<Map<String, Object>> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MapAmlaDetail> mapAmlaDetails = new ArrayList<>();
        for (Map map : list) {
            JSONObject obj = new JSONObject(map);
            // System.out.println("Obj: " + obj.toString());
            MapAmlaDetail hasil = objectMapper.convertValue(map, MapAmlaDetail.class);
            mapAmlaDetails.add(hasil);
        }
        return mapAmlaDetails;
    }

    private Grid<MapAmlaDetail> makeGrid(String date, String accnum, String table, String colDate,
            String colAccNum) {
        // System.out.println(date + " : " + accnum);
        List<Map<String, Object>> isihasil = getAllData(date, accnum,
                table, colDate, colAccNum);
        List<MapAmlaDetail> mapAmlaDetails = new ArrayList<>();
        if (isihasil != null) {
            mapAmlaDetails = mapAllDataDetail(isihasil);
        }

        Grid<MapAmlaDetail> gridAmla = new Grid<>(MapAmlaDetail.class);
        gridAmla.setItems(rps.removeBonus(mapAmlaDetails));
        gridAmla.setWidthFull();
        gridAmla.removeAllColumns();
        // gridAmla.addColumn(MapAmlaDetail::getOlstTxnAcctNumber).setHeader("Account
        // Number")
        // .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(MapAmlaDetail::getOlstTxnCardNumber).setHeader("Card Number")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(MapAmlaDetail::getOlstMccCode).setHeader("MCC")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(MapAmlaDetail::getOlstMechNumber).setHeader("Merchant Number")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(MapAmlaDetail::getOlstPosEntryMode).setHeader("Pos Entry Mode")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(MapAmlaDetail::getOlstTxnPostingDate).setHeader("Posting Date")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(e -> String.format(Locale.US, "%,d", e.getOlstTxnAmt())).setHeader("Amount")
                .setAutoWidth(true).setFlexGrow(1).setSortable(true);
        gridAmla.addColumn(MapAmlaDetail::getOlstTxnDesc).setHeader("Description")
                .setAutoWidth(true).setSortable(true);

        return gridAmla;
    }

    private List<Map<String, Object>> getAllDataSummary(String date, String accnum, String table, String colDate,
            String colAccNum) {

        if (table == null) {
            notif = this.myNotification.error("Table Not Exist");
            notif.open();
            return null;
        }

        String queryString = String.format(
                "select sum(cm.olst_txn_amt),count(cm.olst_txn_code),cm.olst_txn_code,cm.olst_txn_desc,cm.julian_mis_date,cm.olst_txn_bank_nbr from %s cm"
                        +
                        " where Date(cm.%s)= '%s' and cm.%s = '%s'",
                table,
                colDate, date, colAccNum, accnum);

        System.out.println(queryString);

        List<Map<String, Object>> hasilQuery = new ArrayList<>();
        try {
            hasilQuery = jdbcQueryService
                    .executeCustomQuery(queryString + "and cm.olst_txn_card_nbr not like \'9999%\'" +
                            "group by cm.olst_txn_code,cm.julian_mis_date,cm.olst_txn_bank_nbr,cm.olst_txn_desc");
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (hasilQuery.size() == 0) {
            notif = this.myNotification.warning("Data not found");
            notif.open();
        }
        return hasilQuery;

        // Query query = entityManager.createNativeQuery(queryString, kelas);
        // List<?> yow = new ArrayList<>();
        // System.out.println("kelas1: " + kelas.getName());
        // if (kelas.getName().contains("CronosMenyimpangBDetail")) {
        // yow = (List<CronosMenyimpangBDetailTrx6m>) query.getResultList();
        // } else if (kelas.getName().contains("CronosSaldoKreditADetail")) {
        // yow = (List<CronosSaldoKreditADetailTrx6m>) query.getResultList();
        // }
        // // System.out.println("kelas: " + yow.get(0).getClass());
        // JSONArray object = new JSONArray(yow);
        // return yow;
    }

    private List<MapAmlaSummary> mapAllDataDetailSummary(List<Map<String, Object>> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MapAmlaSummary> mapAmlaDetails = new ArrayList<>();
        for (Map map : list) {
            JSONObject obj = new JSONObject(map);
            // System.out.println("Obj: " + obj.toString());
            MapAmlaSummary hasil = objectMapper.convertValue(map, MapAmlaSummary.class);
            mapAmlaDetails.add(hasil);
        }
        return mapAmlaDetails;
    }

    List<MapAmlaSummary> mapAmlaDetailsNew;

    private Grid<MapAmlaSummary> makeGridSummary(String date, String accnum, String table, String colDate,
            String colAccNum) {
        List<Map<String, Object>> isihasil = getAllDataSummary(date, accnum,
                table, colDate, colAccNum);
        List<MapAmlaSummary> mapAmlaDetails = new ArrayList<>();
        if (isihasil != null) {
            mapAmlaDetails = mapAllDataDetailSummary(isihasil);
        }

        mapAmlaDetails = rps.removeExcludeRetailPayment(mapAmlaDetails);
        mapAmlaDetailsNew = rps.removeExcludeRetailPayment(mapAmlaDetails);
        Grid<MapAmlaSummary> gridAmla = new Grid<>(MapAmlaSummary.class);
        gridAmla.setItems(mapAmlaDetails);
        gridAmla.removeAllColumns();
        gridAmla.addColumn(hasil -> String.format(Locale.US, "%,d", hasil.getSum())).setHeader("Total Amount")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(MapAmlaSummary::getCount).setHeader("Frequency")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(e -> convertToGregorianDate(e.getJulianMisDate().toString())).setHeader("Date")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(MapAmlaSummary::getOlstTxnBankNumber).setHeader("Bank Code")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(MapAmlaSummary::getOlstTxnCode).setHeader("Transaction Code")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(hasil -> rps.recastToTypeTrx(hasil.getOlstTxnCode(), hasil.getOlstTxnDesc()))
                .setHeader("Type Trx")
                .setAutoWidth(true).setSortable(true);

        return gridAmla;
    }

    private static LocalDate convertToGregorianDate(String julianDateString) {
        // System.out.println(julianDateString);
        // Parse the year and day components from the Julian date string
        int year = Integer.parseInt(julianDateString.substring(0, 4));
        int dayOfYear = Integer.parseInt(julianDateString.substring(4));

        // Convert to LocalDate
        LocalDate baseDate = LocalDate.of(year, 1, 1);
        LocalDate gregorianDate = baseDate.plusDays(dayOfYear - 1);
        // System.out.println(gregorianDate);

        return gregorianDate;
    }

    private Grid<ResultPaymentRetail> makeGridSummaryTrx() {
        Grid<ResultPaymentRetail> gridAmla = new Grid<>(ResultPaymentRetail.class);
        gridAmla.setWidthFull();
        gridAmla.setItems(rps.getTotalAmount(mapAmlaDetailsNew));
        gridAmla.removeAllColumns();
        gridAmla.addColumn(ResultPaymentRetail::getName).setHeader("Type Trx")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(ResultPaymentRetail::getFreq).setHeader("Frequency")
                .setAutoWidth(true).setSortable(true);
        gridAmla.addColumn(hasil -> String.format(Locale.US, "%,d", hasil.getAmount())).setHeader("Amount")
                .setAutoWidth(true).setSortable(true);

        return gridAmla;
    }

    private List<MapAmlaSummary> removeExcludeRetailPayment(List<MapAmlaSummary> isi) {
        Integer[] retailcode = { 3500, 1005 };
        System.out.println(isi.size());
        List<MapAmlaSummary> newisi = isi.stream()
                // .peek(e -> System.out.println("Initial: " + e))
                .filter(e -> (Arrays.asList(retailcode).contains(e.getOlstTxnCode()))
                        || (e.getOlstTxnCode() >= 8000 && e.getOlstTxnDesc().toUpperCase().contains("PAYMENT")))
                // .peek(e -> System.out.println("After retailcode filter: " + e))
                // .filter(e -> e.getOlstTxnCode() >= 8000 &&
                // e.getOlstTxnDesc().toUpperCase().contains("PAYMENT"))
                .collect(Collectors.toList());
        System.out.println(newisi.size());
        return newisi;
    }

    private String recastToTypeTrx(Integer txncode, String desc) {
        Integer[] retailcode = { 3500, 1005 };
        if (Arrays.asList(retailcode).contains(txncode)) {
            return "Retail";
        } else if (txncode >= 8000 && desc.toUpperCase().contains("PAYMENT")) {
            return "Payment";
        } else {
            return "-";
        }
    }

    private Dialog makeDialogTxnHistory(AmlaRuleResult rrHist) {
        Dialog dialog = new Dialog();
        dialog.setWidthFull();
        dialog.add(new H3("Transaction History"));
        dialog.add(makeGrid(rrHist.getPostDate().toString(), rrHist.getAccNumber(),
                tableHist, "create_date", "olst_txn_acct_nbr"));
        return dialog;
    }

    private Dialog makeDialogTxnSummary(AmlaRuleResult rrHist) {
        Dialog dialog = new Dialog();
        Grid<MapAmlaSummary> grid1 = makeGridSummary(rrHist.getPostDate().toString(), rrHist.getAccNumber(),
                tableHist, "create_date", "olst_txn_acct_nbr");
        Grid<ResultPaymentRetail> grid2 = makeGridSummaryTrx();
        grid2.setAllRowsVisible(true);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(new H3("Transaction Summary"));
        dialogLayout.add(grid2);
        dialogLayout.add(grid1);

        dialog.setWidthFull();
        dialog.add(dialogLayout);
        // dialog.add(new H3("Transaction Summary"));
        // dialog.add(makeGridSummary(rrHist.getPostDate().toString(),
        // rrHist.getAccNumber(),
        // tableHist, "create_date", "olst_txn_acct_nbr"));
        // dialog.add(makeGridSummaryTrx());
        return dialog;
    }
}
