package com.mega.project.utm.views;

import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Models.User;
import com.mega.project.utm.Models.AMLA.AmlaMerchantRuleResult;
import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import com.mega.project.utm.Repositories.AmlaMerchantRuleResultRepository;
import com.mega.project.utm.Repositories.AmlaRuleResultRepository;
import com.mega.project.utm.Repositories.RuleResultRepository;
import com.mega.project.utm.Repositories.UserRepository;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.mega.project.utm.config.VaadinSessionConfiguration;
import com.mega.project.utm.services.CustomRule;
import com.mega.project.utm.services.RoleService;
import com.mega.project.utm.services.SecurityService;
import com.mega.project.utm.views.History.HistoryView;
import com.mega.project.utm.views.QR002.QR002View;
import com.mega.project.utm.views.dashboard.DashboardView;
import com.mega.project.utm.views.test.TestView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.HasMenuItems;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding.Left;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport(value = "./themes/myapp/bootstrap_5.3.2/bootstrap.min.css")
@CssImport(value = "./themes/myapp/new-main-layout.css", themeFor = "vaadin-app-layout")
public class MainLayout extends AppLayout implements BeforeEnterObserver {

    private H2 viewTitle;

    @Autowired
    private SecurityService securityService;

    private VaadinSessionConfiguration vaadinSessionConfiguration;

    private String currentUsername;

    private UserRepository userRepository;

    private AmlaRuleResultRepository amlaRuleResultRepository;
    private AmlaMerchantRuleResultRepository amlaMerchantRuleResultRepository;

    private RuleResultRepository ruleResultRepository;

    private CustomRule customRule;

    private List<AmlaRuleResult> ruleResults;
    private List<AmlaMerchantRuleResult> ruleResultsMerchant;

    private List<RuleResult> qrRuleResults;

    private String role;

    public MainLayout(VaadinSessionConfiguration vaadinSessionConfiguration, UserRepository userRepository,
            CustomRule customRule, AmlaRuleResultRepository amlaRuleResultRepository,
            RuleResultRepository ruleResultRepository,
            AmlaMerchantRuleResultRepository amlaMerchantRuleResultRepository) {
        this.vaadinSessionConfiguration = vaadinSessionConfiguration;
        this.amlaRuleResultRepository = amlaRuleResultRepository;
        RoleService roleService = new RoleService();
        this.userRepository = userRepository;
        this.customRule = customRule;
        this.ruleResultRepository = ruleResultRepository;
        this.amlaMerchantRuleResultRepository = amlaMerchantRuleResultRepository;

        // this.customRule.allRule(LocalDate.now().minusDays(1));

        role = roleService.getRole();

        User optUser = this.userRepository.findByNrik(roleService.getName());
        currentUsername = optUser.getUsername();
        VaadinSession.getCurrent().setAttribute("name", currentUsername);
        VaadinSession.getCurrent().setAttribute("username", roleService.getName());
        VaadinSession.getCurrent().setAttribute("role", role);

        // VaadinSession.getCurrent().setAttribute("username", roleService.getName());
        // System.out.println("username1 :" +
        // VaadinSession.getCurrent().getAttribute("username1"));
        // System.out.println("username2 :" +
        // VaadinSession.getCurrent().getAttribute("username2"));
        this.vaadinSessionConfiguration.setUsername(roleService.getName());
        // System.out.println("MASOK MAIN");
        // this.securityService = securityService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        Image img = new Image("./themes/myapp/components/trans-mega.png", "images/bank-mega-logo.png");
        img.setHeight("10%");
        img.setWidth("20%");
        Button logout = new Button("Log out ", e -> securityService.logout());
        logout.addClassName(LumoUtility.AlignSelf.END);
        // logout.setClassName("logout");
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE, LumoUtility.Width.FULL);
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON);
        menuBar.setClassName(LumoUtility.Width.FULL);
        MenuItem share = createIconItem(menuBar, img, currentUsername,
                null);
        share.addClassName(LumoUtility.JustifyContent.BETWEEN);
        SubMenu shareSubMenu = share.getSubMenu();
        // MenuItem onSocialMedia = shareSubMenu.addItem("On social media");
        // SubMenu socialMediaSubMenu = onSocialMedia.getSubMenu();
        // socialMediaSubMenu.addItem("Facebook");
        // socialMediaSubMenu.addItem("Twitter");
        // socialMediaSubMenu.addItem("Instagram");
        // shareSubMenu.addItem("Profile", e -> UI.getCurrent().navigate("/profile"));
        shareSubMenu.addItem("Logout", e -> securityService.logout());
        // shareSubMenu.getItems().

        menuBar.addThemeVariants(MenuBarVariant.LUMO_END_ALIGNED);
        // menuBar.setWidth("50%");
        // menuBar.setHeight("100%");

        // menuBar.addThemeVariants(MenuBarVariant.LUMO_END_ALIGNED);
        // getElement().setAttribute("coba", "test");

        // toggle.setClassName("nav-bar");
        Tabs tabs = getTabs();
        addToNavbar(true, toggle, viewTitle, menuBar);

    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        Tab coba = new Tab("coba");
        tabs.add(coba);
        return tabs;
    }

    private MenuItem createIconItem(HasMenuItems menu, Image iconName,
            String label, String ariaLabel) {
        return createIconItem(menu, iconName, label, ariaLabel, false);
    }

    private MenuItem createIconItem(HasMenuItems menu, Image iconName,
            String label, String ariaLabel, boolean isChild) {
        // Icon icon = new Icon(iconName);
        Avatar icon = new Avatar("user");
        icon.setImage(iconName.getSrc());
        icon.setClassName(LumoUtility.Margin.XSMALL);

        if (isChild) {
            icon.getStyle().set("width", "var(--lumo-icon-size-s)");
            icon.getStyle().set("height", "var(--lumo-icon-size-s)");
            icon.getStyle().set("marginRight", "var(--lumo-space-s)");
        }

        MenuItem item = menu.addItem(label, e -> {
        });

        item.add(icon);

        // if (ariaLabel != null) {
        // item.setAriaLabel(ariaLabel);
        // }

        // if (label != null) {
        // item.add(new Text(label));
        // }

        return item;
    }

    private void addDrawerContent() {
        H2 appName = new H2("UTM");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Image img = new Image("images/trans-mega.png", "images/bank-mega-logo.png");
        img.setWidth("30%");
        img.setHeight("30%");

        HorizontalLayout hl = new HorizontalLayout(appName, img);

        // hl.setSpacing(true);
        // hl.setFlexGrow(1, appName, img);
        hl.setAlignItems(FlexComponent.Alignment.CENTER);
        hl.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Header header = new Header(hl);
        header.addClassName("header-nav");

        Tabs subViews = new Tabs();
        Tab coba = new Tab("coba");
        subViews.add(coba);

        Scroller scroller = new Scroller(createNavigation());
        Scroller new_scroller = new Scroller(createNavigationNew());

        addToDrawer(header, scroller, createFooter());
    }

    private Component createNavigationNew() {
        return null;
    }

    private SideNav createNavigation() {
        // RoleService roleService = new RoleService();
        // SideNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        qrRuleResults = this.ruleResultRepository.findAllByMemoIsNull();
        Span inboxQRCounter = new Span(String.valueOf(qrRuleResults.size()));
        inboxQRCounter.getElement().getThemeList().add("badge contrast pill");
        inboxQRCounter.getElement().setAttribute("aria-label", "12 unread messages");

        qrRuleResults = this.ruleResultRepository.findByDateAndMemoIsNotNull(LocalDate.of(2023, 9, 16));
        Span historyQRCounter = new Span(String.valueOf(qrRuleResults.size()));
        historyQRCounter.getElement().getThemeList().add("badge contrast pill");
        historyQRCounter.getElement().setAttribute("aria-label", "12 unread messages");

        ruleResults = this.amlaRuleResultRepository
                .findByIsSentAndIsApprovedIsNullMega(false);
        Span inboxAMLACounter = new Span(String.valueOf(ruleResults.size()));
        inboxAMLACounter.getElement().getThemeList().add("badge contrast pill");
        inboxAMLACounter.getElement().setAttribute("aria-label", "12 unread messages");

        ruleResults = this.amlaRuleResultRepository
                .findByIsSentAndIsApprovedIsNullSyariah(false);
        Span inboxAMLASyariahCounter = new Span(String.valueOf(ruleResults.size()));
        inboxAMLASyariahCounter.getElement().getThemeList().add("badge contrast pill");
        inboxAMLASyariahCounter.getElement().setAttribute("aria-label", "12 unread messages");

        ruleResults = this.amlaRuleResultRepository
                .findByPostDateApproved(LocalDate.of(2023, 11, 8));
        Span historyAMLACounter = new Span(String.valueOf(ruleResults.size()));
        historyAMLACounter.getElement().getThemeList().add("badge contrast pill");
        historyAMLACounter.getElement().setAttribute("aria-label", "12 unread messages");

        ruleResults = this.amlaRuleResultRepository
                .findByIsSentMega(true);
        Span inboxAMLACounterApproval = new Span(String.valueOf(ruleResults.size()));
        inboxAMLACounterApproval.getElement().getThemeList().add("badge contrast pill");
        inboxAMLACounterApproval.getElement().setAttribute("aria-label", "12 unread messages");

        ruleResults = this.amlaRuleResultRepository
                .findByIsSentSyariah(true);
        Span inboxAMLASyariahCounterApproval = new Span(String.valueOf(ruleResults.size()));
        inboxAMLASyariahCounterApproval.getElement().getThemeList().add("badge contrast pill");
        inboxAMLASyariahCounterApproval.getElement().setAttribute("aria-label", "12 unread messages");

        ruleResultsMerchant = this.amlaMerchantRuleResultRepository
                .findByIsSentAndIsApprovedIsNullMega(false);
        Span inboxAMLAMerchantCounter = new Span(String.valueOf(ruleResultsMerchant.size()));
        inboxAMLAMerchantCounter.getElement().getThemeList().add("badge contrast pill");
        inboxAMLAMerchantCounter.getElement().setAttribute("aria-label", "12 unread messages");

        ruleResultsMerchant = this.amlaMerchantRuleResultRepository
                .findByIsSentAndIsApprovedIsNullSyariah(false);
        Span inboxAMLAMerchantSyariahCounter = new Span(String.valueOf(ruleResultsMerchant.size()));
        inboxAMLAMerchantSyariahCounter.getElement().getThemeList().add("badge contrast pill");
        inboxAMLAMerchantSyariahCounter.getElement().setAttribute("aria-label", "12 unread messages");

        ruleResultsMerchant = this.amlaMerchantRuleResultRepository
                .findByPostDateApproved(LocalDate.of(2023, 11, 8));
        Span historyAMLAMerchantCounter = new Span(String.valueOf(ruleResultsMerchant.size()));
        historyAMLAMerchantCounter.getElement().getThemeList().add("badge contrast pill");
        historyAMLAMerchantCounter.getElement().setAttribute("aria-label", "12 unread messages");

        ruleResultsMerchant = this.amlaMerchantRuleResultRepository
                .findByIsSent(true);
        Span inboxAMLAMerchantCounterApproval = new Span(String.valueOf(ruleResultsMerchant.size()));
        inboxAMLAMerchantCounterApproval.getElement().getThemeList().add("badge contrast pill");
        inboxAMLAMerchantCounterApproval.getElement().setAttribute("aria-label", "12 unread messages");

        SideNav nav = new SideNav();

        SideNavItem subNavQR = new SideNavItem("QR", "qr");
        SideNavItem subNavAMLA = new SideNavItem("AMLA", "amla");
        SideNavItem subNavAMLAMerchant = new SideNavItem("AMLA Merchant", "amla-merchant");
        SideNavItem subNavUser = new SideNavItem("USER", "user");
        subNavUser.setVisible(false);

        if (role.contains("ADMIN")) {
            subNavUser.setVisible(true);
        }
        // SideNavItem qrInbox = new SideNavItem("Inbox", QR002View.class,
        // VaadinIcon.ENVELOPE.create());
        Icon icon = VaadinIcon.BOOK.create();

        subNavQR.addItem(new SideNavItem("Inbox", QR002View.class, inboxQRCounter));
        subNavQR.addItem(new SideNavItem("History", HistoryView.class, historyQRCounter));

        subNavAMLA.addItem(new SideNavItem("Inbox", "amla/inbox", inboxAMLACounter));
        subNavAMLA.addItem(new SideNavItem("Inbox Syariah", "amla-syariah/inbox", inboxAMLASyariahCounter));
        subNavAMLA.addItem(new SideNavItem("History", "amla/history", historyAMLACounter));
        subNavAMLAMerchant.addItem(new SideNavItem("Inbox", "amla-merchant/inbox", inboxAMLAMerchantCounter));
        subNavAMLAMerchant.addItem(
                new SideNavItem("Inbox Syariah", "amla-merchant-syariah/inbox", inboxAMLAMerchantSyariahCounter));
        subNavAMLAMerchant.addItem(new SideNavItem("History", "amla-merchant/history", historyAMLAMerchantCounter));
        if (!role.contains("ANALYST")) {
            subNavAMLA.addItem(new SideNavItem("Approval", "amla/approval", inboxAMLACounterApproval));
            subNavAMLA.addItem(
                    new SideNavItem("Approval Syariah", "amla-syariah/approval", inboxAMLASyariahCounterApproval));
            subNavAMLAMerchant
                    .addItem(new SideNavItem("Approval", "amla-merchant/approval", inboxAMLAMerchantCounterApproval));
        }
        // nav.addItem(new SideNavItem("Dashboard", DashboardView.class));
        nav.addItem(subNavQR, subNavAMLA, subNavAMLAMerchant, subNavUser);
        // nav.addItem(new SideNavItem("TEST", TestView.class));
        // nav.addItem(new SideNavItem("Tier", TierView.class));
        // nav.addItem(new SideNavItem("Parameter", ParameterView.class));
        // nav.addItem(new SideNavItem("Rule", RuleView.class));
        // nav.addItem(new SideNavItem("Product", ProductView.class));
        // if (roleService.isChecker() || roleService.isAdmin()) {
        // nav.addItem(new SideNavItem("Rule Request", RuleRequestView.class));
        // nav.addItem(new SideNavItem("Product Request", ProducRequestView.class));
        // }
        // nav.setClassName("nav-bar");

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // System.out.println("Enter Event");
        try {
            if (event.getUI() == null) {
                UI.getCurrent().navigate(LoginView.class);
            }
        } catch (Exception e) {
            UI.getCurrent().navigate(LoginView.class);
            // TODO: handle exception
        }
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
