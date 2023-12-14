package com.mega.project.utm.config;

import com.vaadin.flow.server.ServiceException;
import com.vaadin.flow.server.SessionInitEvent;
import com.vaadin.flow.server.SessionInitListener;
import com.vaadin.flow.server.VaadinSession;

public class CustomSessionInitListener implements SessionInitListener {

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        VaadinSession session = event.getSession();
        session.getSession().setMaxInactiveInterval(10); // 10 minutes in seconds
    }
}
