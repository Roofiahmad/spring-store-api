package com.roofiahmad.springstoreapp.util;

import com.roofiahmad.springstoreapp.auth.AuthenticationFailedException;
import com.roofiahmad.springstoreapp.auth.UserPrincipal;
import com.roofiahmad.springstoreapp.common.exception.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    public static UserPrincipal getUserPrincipal() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationFailedException("Unauthenticated");
        }

        var userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if(userPrincipal == null) {
            throw new NotFoundException("User not found");
        }

        return userPrincipal;
    }
}
