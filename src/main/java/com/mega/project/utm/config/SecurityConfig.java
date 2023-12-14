package com.mega.project.utm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mega.project.utm.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity // <1>
@Configuration
public class SecurityConfig extends VaadinWebSecurity { // <2>

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll();
            auth.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/**")).permitAll();
            auth.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/session-end")).permitAll();
            auth.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/login")).permitAll();
            auth.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/bootstrap_5.3.2/**")).permitAll();
        }).formLogin()
                .loginPage("/login")
                .failureHandler(authenticationFailureHandler())
                .permitAll(); // .maxSessionsPreventsLogin(true)); //
        // <3>
        super.configure(http);

        setLoginView(http, LoginView.class); // <4>

    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/api/**").requestMatchers("/h2-console/**");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    // @Bean
    // public UserDetailsService users() {
    // UserDetails user = User.withUsername("Alejandro")
    // .password("{noop}p")
    // .roles("ADMIN")
    // .build();
    // return new InMemoryUserDetailsManager(user); // <5>
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }
}
