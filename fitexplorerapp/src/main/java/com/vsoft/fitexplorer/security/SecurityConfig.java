package com.vsoft.fitexplorer.security;

import com.vsoft.fitexplorer.UserProfile;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.vsoft.fitexplorer.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableAsync
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationSuccessHandler customLoginSuccessHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/activities/**").hasAnyRole(new String[]{Roles.TRAINEE, Roles.ADMIN})
                                .requestMatchers("/swagger-ui.html").hasAnyRole(new String[]{Roles.TRAINEE, Roles.ADMIN})
                                .requestMatchers("/swagger-ui/*").hasAnyRole(new String[]{Roles.TRAINEE, Roles.ADMIN})
                                .requestMatchers("/calendar.js").hasAnyRole(new String[]{Roles.TRAINEE, Roles.ADMIN})
                                .requestMatchers("/region.html").hasAnyRole(new String[]{Roles.TRAINEE, Roles.ADMIN})
                                .requestMatchers("/cal.html").hasAnyRole(new String[]{Roles.TRAINEE, Roles.ADMIN})
                                .requestMatchers("/error").hasAnyRole(new String[]{Roles.TRAINEE, Roles.ADMIN})
                                .requestMatchers("/favicon.ico").hasAnyRole(new String[]{Roles.TRAINEE, Roles.ADMIN})

                                .requestMatchers("/**").hasAnyRole(new String[]{Roles.TRAINEE, Roles.ADMIN})




                )
                .formLogin( x -> {
                    x.defaultSuccessUrl("/swagger-ui.html", true);
                    x.loginPage("/login.html").permitAll();
                    x.loginProcessingUrl("/perform_login");
                    x.successHandler(customLoginSuccessHandler);
                    x.failureUrl("/login.html?error=true");
                })
                .logout(x -> x.logoutUrl("/perform_logout"))
                .exceptionHandling(h ->h.authenticationEntryPoint(authenticationEntryPoint).
                        accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserProfile getUser(HttpServletRequest request) {
        UserProfile userProfile = new UserProfile();
        var principal = request.getUserPrincipal();
        if (principal instanceof UsernamePasswordAuthenticationToken token) {
            userProfile.setAdmin(token.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals(Roles.ROLE_PREFIX + Roles.ADMIN)));
            userProfile.setMerchant(token.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals(Roles.ROLE_PREFIX + Roles.TRAINEE)));
        }
        userProfile.setUserId(1);
        userProfile.setUserName(principal.getName());
        return userProfile;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}

