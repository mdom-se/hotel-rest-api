package com.demo.hotel.security.dto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class AuthRequest {
  @NotNull @Length(min = 3, max = 10)
  private String username;

  @NotNull @Length(min = 5, max = 10)
  private String password;

  public String getUsername() {
    return username;
  }

  public AuthRequest setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public AuthRequest setPassword(String password) {
    this.password = password;
    return this;
  }
}