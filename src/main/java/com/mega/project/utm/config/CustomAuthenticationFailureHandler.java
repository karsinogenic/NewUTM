package com.mega.project.utm.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(exception);

        super.setDefaultFailureUrl(targetUrl);
        super.onAuthenticationFailure(request, response, exception);
    }

    protected String determineTargetUrl(AuthenticationException exception) {
        String errorMessage = "";
        System.out.println(exception.getMessage());
        if (exception.getMessage().equals("Password")) {
            errorMessage = "?error=password";
        } else if (exception.getMessage().equals("Timeout")) {
            errorMessage = "?error=timeout";
        } else if (exception.getMessage().equals("Expired")) {
            errorMessage = "?error=expired";
        }
        // errorMessage = "?error=password";
        return "/login" + errorMessage;
    }
}
