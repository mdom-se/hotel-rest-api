package com.demo.hotel.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthWebSecurityConfiguration {

    static final String USER_READ = "USER_READ";
    static final String USER_WRITE_READ = "USER_WRITE_READ";

    private final AppBasicAuthenticationEntryPoint authenticationEntryPoint;


    public BasicAuthWebSecurityConfiguration(AppBasicAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/hotels/**", "/amenities/**").hasAnyRole(USER_READ, USER_WRITE_READ)
                .and()
                .authorizeRequests()
                .antMatchers("/hotels/**", "/amenities/**").hasRole(USER_WRITE_READ)
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(
            @Value("${user.rest.password:hello}") String userRestPassword) {
        UserDetails userRw = User
                .withUsername("user")
                .password(passwordEncoder().encode(userRestPassword))
                .roles(USER_WRITE_READ)
                .build();
        UserDetails user = User
                .withUsername("read-user")
                .password(passwordEncoder().encode(userRestPassword))
                .roles(USER_READ)
                .build();
        return new InMemoryUserDetailsManager(userRw, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
