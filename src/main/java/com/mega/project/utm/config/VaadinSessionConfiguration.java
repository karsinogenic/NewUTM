package com.mega.project.utm.config;

import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Models.User;
import com.mega.project.utm.Repositories.HistoryMemoRepository;
import com.mega.project.utm.Repositories.RuleResultRepository;
import com.mega.project.utm.Repositories.UserRepository;
import com.mega.project.utm.services.RoleService;
import com.vaadin.flow.server.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Configures Vaadin to work properly with sessions.
 */
@Component
public class VaadinSessionConfiguration
        implements VaadinServiceInitListener, SystemMessagesProvider, SessionDestroyListener, SessionInitListener,
        UIInitListener {

    // private final String relativeSessionExpiredUrl;
    private RuleResultRepository ruleResultRepository;

    private UserRepository userRepository;

    private final SessionRegistry sessionRegistry;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public VaadinSessionConfiguration(SessionRegistry sessionRegistry, RuleResultRepository ruleResultRepository,
            UserRepository userRepository) {
        this.sessionRegistry = sessionRegistry;
        this.ruleResultRepository = ruleResultRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        VaadinSession session = event.getSession();
        session.getSession().setMaxInactiveInterval(600); // 10 minutes in seconds
    }

    @Override
    public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
        var messages = new CustomizedSystemMessages();
        // Redirect to a specific screen when the session expires. In this particular
        // case we don't want to logout
        // just yet. If you would like the user to be completely logged out when the
        // session expires, this URL
        // should the logout URL.
        // messages.setSessionExpiredURL(relativeSessionExpiredUrl);
        return messages;
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        String username1 = (String) event.getSession().getAttribute("username");
        try {
            User user = this.userRepository.findByNrik(username1);
            user.setLastLogin(LocalDateTime.now());
            this.userRepository.save(user);
        } catch (Exception e) {
            // TODO: handle exception
        }
        List<RuleResult> listRuleResults = this.ruleResultRepository.findByLockedBy(username1);
        String role = (String) event.getSession().getAttribute("role");
        for (RuleResult ruleResult : listRuleResults) {
            ruleResult.setLockedBy(null);
        }
        this.ruleResultRepository.saveAll(listRuleResults);
        // if (username != null) {
        // String username = this.username; // Get the username
        // // Get the roles of the authenticated user
        // System.out.println("Destroy: " + username);

    }

    // System.out.println("Destroy1: ");

    // // We also want to destroy the underlying HTTP session since it is the one
    // that
    // // contains the authentication
    // // token.
    // try {
    // event.getSession().getSession().invalidate();
    // } catch (Exception ignore) {
    // // Session was probably already invalidated.
    // }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().setSystemMessagesProvider(this);
        event.getSource().addSessionDestroyListener(this);
        event.getSource().addSessionInitListener(this);
        event.getSource().addUIInitListener(this);
    }

    @Override
    public void uiInit(UIInitEvent event) {
        // System.out.println("UI Established");
    }

}
