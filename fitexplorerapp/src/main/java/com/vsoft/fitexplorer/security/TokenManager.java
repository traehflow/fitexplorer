package com.vsoft.fitexplorer.security;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import com.vsoft.fitexplorer.jpl.entity.UserData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class TokenManager implements Serializable {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 7008375124389347049L;
    public static final long TOKEN_VALIDITY = 10 * 60 * 60;
    public static final String USER_ID = "user_id";
    @Value("${secret}")
    private String jwtSecret;
    public String generateJwtToken(UserData userData) {
        Map<String, Object> claims = Map.of("additional_role", "role",
                USER_ID, userData.getId());
        long current= System.currentTimeMillis();
        return Jwts.builder().setClaims(claims).setSubject(userData.getUsername())
                .setIssuedAt(new Date(current))
                .setSubject(userData.getUsername())
                .setExpiration(new Date(current + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }
    public boolean validateJwtToken(String token, String user) {
        String username = getUsernameFromToken(token);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
        boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(user) && !isTokenExpired);
    }
    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public int getUserIdFromToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
        return (Integer)claims.get(USER_ID);
    }


    public static String getTokenFromRequest(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null && request.getCookies() != null) {
            var cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("auth-token")).findFirst();
            if (cookie.isPresent()) {
                tokenHeader = "Bearer " + cookie.get().getValue();
            }
        }

        String token = null;
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
        }
        return token;
    }

}