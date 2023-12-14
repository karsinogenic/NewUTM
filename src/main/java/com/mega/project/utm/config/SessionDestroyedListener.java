package com.mega.project.utm.config;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

@Component
public class SessionDestroyedListener implements ApplicationListener<SessionDestroyedEvent> {

    private final SessionRegistry sessionRegistry;

    public SessionDestroyedListener(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        sessionRegistry.getAllPrincipals().forEach(principal -> {
            // Handle session timeout here
            String username = principal.toString();
            System.out.println("log user: " + username);
            // Perform necessary actions before session timeout
        });
    }
}
