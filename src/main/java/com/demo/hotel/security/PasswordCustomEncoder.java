package com.demo.hotel.security;

import com.demo.hotel.security.dto.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class PasswordCustomEncoder {
  public static final String USER_READ = "USER_READ";
  public static final String USER_WRITE_READ = "USER_WRITE_READ";
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(8);
  }

  @Bean
  public UserDetailsService userDetailsService(
      @Value("${user.rest.password:hello}") String userRestPassword,
      PasswordEncoder passwordEncoder) {
    // 1.- Configuring authentication (in memory)
    UserDetails userRw = AppUser
        .withUsername("user")
        .password(passwordEncoder.encode(userRestPassword))
        .roles(USER_WRITE_READ)
        .build()
        ;
    UserDetails user = AppUser
        .withUsername("read-user")
        .password(passwordEncoder.encode(userRestPassword))
        .roles(USER_READ)
        .build();
    return new InMemoryUserDetailsManager(userRw, user);
  }
}
