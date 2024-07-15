package com.mega.project.utm.config;

import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.mega.project.utm.Models.User;
import com.mega.project.utm.Repositories.UserRepository;
import com.mega.project.utm.services.AESEncryptDecrypt;
import com.vaadin.flow.server.VaadinSession;

import jakarta.servlet.ServletContext;

@Component
public class NewCustomValidation extends SimpleUrlAuthenticationFailureHandler implements AuthenticationProvider {
    // @Autowired
    // private ServletContext context;

    // @Autowired
    // private FTPComponents ftpComponents;
    private UserRepository userRepository;

    @Value("${authentication.url}")
    private String authUrl;

    @Value("${authentication.enable}")
    private Boolean useAuth;

    public NewCustomValidation(UserRepository userRepository) {
        // this.ftpComponents = ftpComponents;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AESEncryptDecrypt aesEncryptDecrypt = new AESEncryptDecrypt();
        String message = "";
        try {
            User user = this.userRepository.findByNrik(authentication.getName());
            // System.out.println(context.getVirtualServerName());
            // System.out.println(ftpComponents.getAuthUrl());
            // System.out.println("ROLE_" + user.getRole());
            // Boolean cek = false;
            // Boolean cek = post(ftpComponents.getAuthUrl(),
            // "{\"userName\":" + authentication.getName() + ",\"passWord\":\"" +
            // authentication.getCredentials()
            // + "\"}");

            Boolean cek = false;
            // Boolean cek = post("/api/test",
            // authentication.getName());
            if (useAuth) {
                cek = post(authUrl,
                        authentication.getName());
            } else {
                cek = true;
            }
            // Boolean cek = post("http://localhost:8083/api/test",
            // authentication.getName());

            if (cek) {

                if (user.getExpiredOn().isBefore(LocalDateTime.now())) {
                    // System.out.println("coba1");
                    message = "Expired";
                    throw new BadCredentialsException("Expired");
                }

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities
                        .add(new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole()));

                user.setPassword(
                        aesEncryptDecrypt.encrypt(authentication.getCredentials().toString(), "MEGA2023MEGA2023",
                                "MEGA2023MEGA2023"));

                user.setExpiredOn(LocalDateTime.now().plusMonths(3));
                this.userRepository.save(user);

                // Set username and role attributes in the Vaadin session
                // System.out.println(authentication.getName());
                // VaadinSession.getCurrent().setAttribute("role", "ROLE_" + user.getRole());
                // System.out.println("login");
                return new UsernamePasswordAuthenticationToken(authentication.getName(),
                        authentication.getCredentials().toString(), authorities);
            } else {
                message = "Password";
                throw new BadCredentialsException("Password");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            throw new BadCredentialsException(message.length() > 0 ? message : "Timeout");
        }
        // System.out.println("asda");
        // System.out.println(authentication.getName());

    }

    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    public Boolean post(String uri, String data) throws Exception {
        System.out.println(data);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(2)) // Set timeout here (10 seconds in this example)
                .POST(BodyPublishers.ofString(data))
                .header("Content-type", "application/json")
                .header("Authorization", getBasicAuthenticationHeader("CardSysAscore", "Pass1234!!"))
                .build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body().toString());
        Boolean hasil = response.body().toString().contains("200") && response.statusCode() == 200;
        return hasil;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
