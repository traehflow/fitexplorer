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

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void postConstruct() {
/*        userRepository.save(new UserData("wolfheart@mail.com",
                "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                Set.of("TRAINEE")));
        userRepository.save(new UserData("petersecada@merchant.com",
                "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                Set.of("MERCHANT")));
        userRepository.save(new UserData("admin",
                "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                Set.of("ADMIN")));*/
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
        var TRAINEE = new SimpleGrantedAuthority("ROLE_TRAINEE");
        Map<String, SimpleGrantedAuthority> roleMap = Map.of("ADMIN",  new SimpleGrantedAuthority("ROLE_ADMIN"),
                "TRAINEE", new SimpleGrantedAuthority("ROLE_TRAINEE"));
        UserData user = userRepository.loadUser(username);
        if(user != null) {
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    CollectionUtils.emptyIfNull(user.getRole().stream().map(roleMap::get).filter(Objects::nonNull).collect(Collectors.toSet())));
        }
        if ("wolfheart@mail.com".equals(username)) {
            return new User("wolfheart@mail.com",
                    "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    List.of(TRAINEE));
        } else if ("petersecada@mail.com".equals(username)) {
            return new User("petersecada@mail.com",
                    "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    List.of(TRAINEE));
        } else if ("admin".equals(username)) {
            return new User("admin",
                    "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    List.of(ADMIN));
        } else {
            throw new UsernameNotFoundException("UserData not found with username: " + username);
        }

    }
}