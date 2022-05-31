package com.ko.mediate.HC.jwt;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.tutoring.application.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
  private static final String AUTHORITIES_KEY = "authority";
  private static final String ACCOUNT_ID_KEY = "accountId";
  private static final String ACCOUNT_EMAIL_KEY = "accountEmail";
  private static final String ROLE_KEY = "role";
  private final String secret;
  private final long accessTokenValidityInMilliseconds;
  private final long refreshTokenValidityInMilliseconds;
  private Key key; // 복호화된 키가 들어감
  private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

  public TokenProvider(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.access.expired-time}") long accessTokenValidityInMilliseconds,
      @Value("${jwt.refresh.expired-time}") long refreshTokenValidityInMilliseconds) {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.secret = secret;
    this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
    this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
  }

  private String createToken(
      Long accountId, String accountEmail, List<RoleType> roles, long tokenValidityInMilliseconds) {

    long now = (new Date()).getTime();
    Date validity = new Date(now + tokenValidityInMilliseconds);

    return Jwts.builder()
        .claim(ACCOUNT_ID_KEY, String.valueOf(accountId))
        .claim(ACCOUNT_EMAIL_KEY, accountEmail)
        .claim(
            ROLE_KEY,
            String.join(",", roles.stream().map(RoleType::toString).collect(Collectors.toList())))
        .signWith(key, SignatureAlgorithm.HS512)
        .setExpiration(validity)
        .compact();
  }

  public String createAccessToken(Long accountId, String accountEmail, List<RoleType> roles) {
    return createToken(accountId, accountEmail, roles, this.accessTokenValidityInMilliseconds);
  }

  public String createRefreshToken(Long accountId, String accountEmail, List<RoleType> roles) {
    return createToken(accountId, accountEmail, roles, this.refreshTokenValidityInMilliseconds);
  }

  public UserInfo getUserInfoFromToken(String token) {
    Claims claims = decode(token);
    return new UserInfo(
        Long.valueOf((String) claims.get(ACCOUNT_ID_KEY)),
        (String) claims.get(ACCOUNT_EMAIL_KEY),
        (String) claims.get(ROLE_KEY));
  }

  public Authentication getAuthentication(String token) {
    Claims claims = decode(token);

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    User principal = new User(claims.get(ACCOUNT_ID_KEY).toString(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      throw new io.jsonwebtoken.security.SecurityException("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      throw new ExpiredJwtException(null, null, "만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      throw new UnsupportedJwtException("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("JWT 토큰이 잘못되었습니다.");
    }
  }

  public String createAccessTokenIfExpired(
      String token, Long accountId, String accountEmail, List<RoleType> roles) {
    try {
      validateToken(token);
      return token;
    } catch (ExpiredJwtException e) {
      return createAccessToken(accountId, accountEmail, roles);
    }
  }

  public long getExpiredTime(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration()
            .getTime()
        - new Date().getTime();
  }

  public Claims decode(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }
}
