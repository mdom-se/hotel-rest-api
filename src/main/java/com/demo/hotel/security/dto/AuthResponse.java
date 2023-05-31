package com.demo.hotel.security.dto;

public class AuthResponse {
  private String username;
  private String accessToken;

  public String getUsername() {
    return username;
  }

  public AuthResponse setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public AuthResponse setAccessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }
}
