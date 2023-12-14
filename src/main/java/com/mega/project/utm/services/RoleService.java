package com.mega.project.utm.services;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

public class RoleService {

    public String getName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public Boolean isUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println(authentication.getAuthorities().contains("[ROLE_CHECKER]"));
        Collection<? extends GrantedAuthority> list = authentication.getAuthorities();
        Boolean role_check = false;

        for (GrantedAuthority grantedAuthority : list) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                role_check = true;
            }
        }
        return role_check;
    }

    public Boolean isChecker() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println(authentication.getAuthorities().contains("[ROLE_CHECKER]"));
        Collection<? extends GrantedAuthority> list = authentication.getAuthorities();
        Boolean role_check = false;

        for (GrantedAuthority grantedAuthority : list) {
            if (grantedAuthority.getAuthority().equals("ROLE_CHECKER")) {
                role_check = true;
            }
        }
        return role_check;
    }

    public Boolean isMaker() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println(authentication.getAuthorities().contains("[ROLE_CHECKER]"));
        Collection<? extends GrantedAuthority> list = authentication.getAuthorities();
        Boolean role_check = false;

        for (GrantedAuthority grantedAuthority : list) {
            if (grantedAuthority.getAuthority().equals("ROLE_MAKER")) {
                role_check = true;
            }
        }
        return role_check;
    }

    public Boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println(authentication.getAuthorities().contains("[ROLE_CHECKER]"));
        Collection<? extends GrantedAuthority> list = authentication.getAuthorities();
        Boolean role_check = false;

        for (GrantedAuthority grantedAuthority : list) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                role_check = true;
            }
        }
        return role_check;
    }

}
