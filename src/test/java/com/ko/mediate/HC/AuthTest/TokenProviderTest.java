package com.ko.mediate.HC.AuthTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ko.mediate.HC.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class TokenProviderTest {
  TokenProvider tokenProvider =
      new TokenProvider(
          "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK",
          6000L);

  @DisplayName("토큰에 정보가 담기는지 테스트")
  @Test
  void createTokenTest() {
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(
            "test",
            "",
            List.of(
                new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_TUTOR")));
    String token = tokenProvider.createToken(authentication);
    Claims claims = tokenProvider.decode(token);
    assertThat(claims.get("account")).isEqualTo("test");
    List<String> authorities =
        Arrays.stream(claims.get("authority").toString().split(",")).collect(Collectors.toList());
    assertThat(authorities.contains("ROLE_USER"));
    assertThat(authorities.contains("ROLE_TUTOR"));
  }
}
