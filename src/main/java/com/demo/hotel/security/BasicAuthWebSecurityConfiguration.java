package com.demo.hotel.security;

import static com.demo.hotel.security.PasswordCustomEncoder.USER_READ;
import static com.demo.hotel.security.PasswordCustomEncoder.USER_WRITE_READ;
import static org.springframework.security.config.Customizer.withDefaults;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class BasicAuthWebSecurityConfiguration {


  private final JwtTokenFilter jwtTokenFilter;

  private final JwtAuthenticationProvider customAuthenticationProvider;

  public BasicAuthWebSecurityConfiguration(JwtTokenFilter jwtTokenFilter,
      JwtAuthenticationProvider customAuthenticationProvider) {
    this.jwtTokenFilter = jwtTokenFilter;
    this.customAuthenticationProvider = customAuthenticationProvider;
  }

  // adding our custom authentication providers
  // authentication manager will call these custom provider's
  // authenticate methods from now on.
  @Autowired
  void registerProvider(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(customAuthenticationProvider);
  }


  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/actuator/**", "/auth").permitAll()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/hotels/**", "/amenities/**").hasAnyRole(USER_READ, USER_WRITE_READ)
        .and()
        .authorizeRequests()
        .antMatchers("/hotels/**", "/amenities/**").hasRole(USER_WRITE_READ)
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint(
            (request, response, ex) ->
                response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    ex.getMessage()
                )
        );
    return http.cors(withDefaults()).build();
  }


  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }


}
