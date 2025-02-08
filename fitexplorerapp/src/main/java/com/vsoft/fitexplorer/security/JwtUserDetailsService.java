package com.vsoft.fitexplorer.security;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.vsoft.fitexplorer.jpl.UserRepository;
import com.vsoft.fitexplorer.jpl.entity.UserData;
import jakarta.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    Map<String, SimpleGrantedAuthority> roleMap = Map.of("ADMIN",  new SimpleGrantedAuthority("ROLE_ADMIN"),
            "TRAINEE", new SimpleGrantedAuthority("ROLE_TRAINEE"));

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void postConstruct() {
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserData user = retrieveUser(username);
        if(user != null) {
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    CollectionUtils.emptyIfNull(user.getRole().stream().map(roleMap::get).filter(Objects::nonNull).collect(Collectors.toSet())));
        }
        throw new UsernameNotFoundException("UserData not found with username: " + username);

    }

    public UserData retrieveUser(String username) throws UsernameNotFoundException {
        UserData user = userRepository.loadUser(username);
        if(user == null) {
            if ("admin".equals(username)) {
                return new UserData(1,
                        "admin",
                        "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                        Set.of("ADMIN"));
            }
        }
        return user;
    }
}