package com.demo.hotel.restcontroller;

import com.demo.hotel.security.JwtTokenUtil;
import com.demo.hotel.security.dto.AuthRequest;
import com.demo.hotel.security.dto.AuthResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

  private final AuthenticationManager authManager;
  private final JwtTokenUtil jwtUtil;

  public AuthRestController(AuthenticationManager authManager, JwtTokenUtil jwtUtil) {
    this.authManager = authManager;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
    try {
      Authentication authentication = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getUsername(), request.getPassword())
      );

      if (authentication.isAuthenticated()) {
        String username = (String) authentication.getPrincipal();
        String accessToken = jwtUtil.generate(username);
        AuthResponse response = new AuthResponse()
            .setUsername(username)
            .setAccessToken(accessToken);
        return ResponseEntity.ok().body(response);
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}