package com.demo.hotel.security.dto;


import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class AppUser extends org.springframework.security.core.userdetails.User {

  public AppUser(String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public AppUser(String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired,
      boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }


}
