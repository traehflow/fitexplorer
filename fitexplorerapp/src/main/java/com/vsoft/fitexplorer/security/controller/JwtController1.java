package com.vsoft.fitexplorer.security.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtController1 {

  /*  @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseModel> createToken(@RequestBody JwtRequestModel
                                                request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new
                            UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword())
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JwtResponseModel(jwtToken));
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(HttpSecurity http) throws Exception {
            AuthenticationManagerBuilder auth =
                    http.getSharedObject(AuthenticationManagerBuilder.class);
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
            return auth.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/

}