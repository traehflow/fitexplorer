package com.vsoft.fitexplorer.security;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String token = tokenManager.getTokenFromRequest(request);
        String username = null;
        if (token != null) {
            try {
                username = tokenManager.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
                expireAuthCookie(response);
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
                expireAuthCookie(response);
            }
        } else {
            System.out.println("Bearer String not found in token");
            filterChain.doFilter(request, response);
            return;
        }
        if (null != username &&SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (tokenManager.validateJwtToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken
                        authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private static void expireAuthCookie(HttpServletResponse response) {
        var cookie = new Cookie("auth-token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}