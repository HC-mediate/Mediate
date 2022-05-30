package com.ko.mediate.HC.jwt;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
  private CustomUserDetails principal;

  public CustomAuthenticationToken(
      CustomUserDetails principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }
}
