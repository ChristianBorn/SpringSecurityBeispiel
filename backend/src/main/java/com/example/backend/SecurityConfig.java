package com.example.backend;

import com.example.backend.user.AppUser;
import com.example.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public PasswordEncoder encoder() {
        return passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/app-users").permitAll()
                .antMatchers(HttpMethod.GET, "/api/app-users/me").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/cats",
                        "/api/app-users/login",
                        "/api/app-users/logout"
                        ).hasAnyRole("User","Basic")
                .antMatchers(HttpMethod.POST, "/api/cats").hasAnyRole("User","Basic")
                .anyRequest().denyAll()
                .and().formLogin()
                .and().build();
    }


    @Bean
    public UserDetailsManager userDetailsService() {
        return new UserDetailsManager() {
            static final String EXCEPTIONMESSAGE = "Method invocation not possible";
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser userByName = userService.findByUsername(username);
                if (userByName == null) {
                    throw new UsernameNotFoundException("Username not found");
                }
                return User.builder()
                        .username(username)
                        .password(userByName.passwordBcrypt())
                        .roles(userByName.role())
                        .build();
            }

            @Override
            public void createUser(UserDetails user) {
                throw new UnsupportedOperationException(EXCEPTIONMESSAGE);
            }

            @Override
            public void updateUser(UserDetails user) {
                throw new UnsupportedOperationException(EXCEPTIONMESSAGE);
            }

            @Override
            public void deleteUser(String username) {
                throw new UnsupportedOperationException(EXCEPTIONMESSAGE);
            }

            @Override
            public void changePassword(String oldPassword, String newPassword) {
                throw new UnsupportedOperationException(EXCEPTIONMESSAGE);
            }

            @Override
            public boolean userExists(String username) {
                throw new UnsupportedOperationException(EXCEPTIONMESSAGE);
            }
        };

    }



}

