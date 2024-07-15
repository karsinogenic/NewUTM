package com.mega.project.utm.config;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import com.vaadin.flow.server.CustomizedSystemMessages;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.SessionDestroyEvent;
import com.vaadin.flow.server.SessionDestroyListener;
import com.vaadin.flow.server.SystemMessages;
import com.vaadin.flow.server.SystemMessagesInfo;
import com.vaadin.flow.server.SystemMessagesProvider;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;

@Component
public class VaadinSessionListener
        implements VaadinServiceInitListener, SystemMessagesProvider, SessionDestroyListener {

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        System.out.println("Destroy the world by " + VaadinSession.getCurrent().getAttribute("username"));
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'sessionDestroy'");
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
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getSystemMessages'");
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().setSystemMessagesProvider(this);
        event.getSource().addSessionDestroyListener(this);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'serviceInit'");
    }

    // private final SessionRegistry sessionRegistry;

    // public SessionDestroyedListener(SessionRegistry sessionRegistry) {
    // this.sessionRegistry = sessionRegistry;
    // }

    // @Override
    // public void onApplicationEvent(SessionDestroyedEvent event) {
    // // System.out.println("shit");
    // sessionRegistry.getAllPrincipals().forEach(principal -> {
    // // Handle session timeout here
    // String username = principal.toString();
    // System.out.println("log user: " + username);
    // // Perform necessary actions before session timeout
    // });
    // }
}
