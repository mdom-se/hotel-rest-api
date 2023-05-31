package com.demo.hotel.security;


import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {
  // Simple JWT implementation
  private final JwtTokenUtil jwtTokenUtil;

  private final UserDetailsService userDetailsService;

  public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsService = userDetailsService;
  }


  // Spring Security will call this method during filter chain execution
  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {

    // trying to find Authorization header
    final String authorizationHeader = httpServletRequest.getHeader("Authorization");
    if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer")){
      // if Authorization header does not exist, then skip this filter
      // and continue to execute next filter class
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    final String token = authorizationHeader.split(" ")[1].trim();
    if (!jwtTokenUtil.validate(token)) {
      // if token is not valid, then skip this filter
      // and continue to execute next filter class.
      // This means authentication is not successful since token is invalid.
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    // Authorization header exists, token is valid. So, we can authenticate.
    String username = jwtTokenUtil.getUsername(token);

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    // initializing UsernamePasswordAuthenticationToken with its 3 parameter constructor
    // because it sets super.setAuthenticated(true); in that constructor.
    UsernamePasswordAuthenticationToken upassToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    upassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
    // finally, give the authentication token to Spring Security Context
    SecurityContextHolder.getContext().setAuthentication(upassToken);

    // end of the method, so go for next filter class
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}