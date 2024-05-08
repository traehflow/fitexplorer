package com.vsoft.fitexplorer;

import com.vsoft.fitexplorer.security.JwtUserDetailsService;
import com.vsoft.fitexplorer.security.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private TokenManager tokenManager;

    public AuthenticationSuccessHandler() {
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        org.springframework.security.web.authentication.AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);
        Cookie cookie = new Cookie("auth-token", jwtToken);
        response.addCookie(cookie);

        if(authentication.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals(("ROLE_TRAINEE")))){
            //Successful authentication
            response.sendRedirect("/swagger-ui.html");

        }

        if(authentication.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals(("ROLE_ADMIN")))){
            response.sendRedirect("/adminpage.html");

        }


    }
}
