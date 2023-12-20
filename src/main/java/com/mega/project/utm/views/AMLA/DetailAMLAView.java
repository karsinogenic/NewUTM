package com.mega.project.utm.views.AMLA;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mega.project.utm.Models.AllData;
import com.mega.project.utm.Models.HistoryMemo;
import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangA;
import com.mega.project.utm.Models.AMLA.MerchMenyimpangB;
import com.mega.project.utm.Models.AMLA.RefundPoin;
import com.mega.project.utm.Repositories.AmlaRuleResultRepository;
import com.mega.project.utm.Repositories.HistoryMemoRepository;
import com.mega.project.utm.services.JdbcQueryService;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.MyNotification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
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

        if (param1[0].equals("TRM005")) {
            query += "MERCH_MENYIMPANG_B\" ";
        } else if (param1[0].equals("TRM004")) {
            query += "MERCH_MENYIMPANG_A\" ";
        } else if (param1[0].equals("TRM007")) {
            query += "MENYIMPANG_B\" ";
        } else if (param1[0].equals("TRM001")) {
            query += "MENYIMPANG_A\" ";
        } else {
            query += "REFUND_POIN\" ";
        }

        query += "where \"id\" = '" + param1[1] + "'";

        AmlaRuleResult ruleResult = this.amlaRuleResultRepository.findById(param1[2]).get();
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
        for (String key : jsonData.keySet()) {
            TextField textField = new TextField(key);
            String value = jsonData.get(key).toString();
            if (key.contains("julianDate")) {
                textField = new TextField("Date");
                LocalDate localDate = ordinalToGregorian(Integer.valueOf(value.substring(0, 4)),
                        Integer.valueOf(value.substring(4, value.length())));
                value = localDate.toString();
            }
            String doubleKey[] = { "avg", "Amount", "total", "creditLimit" };
            if (containsWord(key, doubleKey)) {
                double doubleValue = Double.parseDouble(value);
                long longValue = (long) doubleValue;
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                value = numberFormat.format(longValue).toString();
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
                UI.getCurrent().getPage().setLocation("/amla/inbox");

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
                UI.getCurrent().getPage().setLocation("/amla/inbox");
                ;

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

        memoCombo.add(memoApproval);

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
        // memoDiv.addClassName(Vaadin);
        add(memoCombo, formLayout, memoDiv);
    }

    private Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }

    public DetailAMLAView(JdbcQueryService jdbcQueryService, AmlaRuleResultRepository amlaRuleResultRepository,
            MyNotification myNotification, HistoryMemoRepository historyMemoRepository) {
        this.jdbcQueryService = jdbcQueryService;
        this.amlaRuleResultRepository = amlaRuleResultRepository;
        this.myNotification = myNotification;
        this.historyMemoRepository = historyMemoRepository;

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
                UI.getCurrent().getPage().setLocation("/amla/inbox");
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
        comboBox.setItems("Consider Genuine", "Genuine", "Suspect", "Fraud");

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

}
