package com.example.parking.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String obtenerEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated() && !authentication.getAuthorities().isEmpty()){
            return authentication.getName();
        }
        return null;
    }
}
