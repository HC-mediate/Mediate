package com.ko.mediate.HC.jwt;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final TokenGenerator tokenGenerator;

    private final TokenStorage tokenStorage;

    private final JwtSecretKey jwtSecretKey;

    public String getAccessToken(Account account) {
        CustomJwtClaim customJwtClaim = CustomJwtClaim.of(account.getId(), account.getEmail(), account.getNickname(), account.getRoles());
        String accessToken = tokenGenerator.createAccessToken(customJwtClaim);
        tokenStorage.saveAccessToken(accessToken, account.getId());
        return accessToken;
    }

    public String getAccessToken(UserInfo userInfo) {
        CustomJwtClaim customJwtClaim = CustomJwtClaim.of(userInfo.getAccountId(), userInfo.getAccountEmail(), userInfo.getAccountNickname(), userInfo.getRoles());
        String accessToken = tokenGenerator.createAccessToken(customJwtClaim);
        tokenStorage.saveAccessToken(accessToken, userInfo.getAccountId());
        return accessToken;
    }

    public String getRefreshToken(Account account) {
        CustomJwtClaim customJwtClaim = CustomJwtClaim.of(account.getId(), account.getEmail(),
                account.getNickname(), account.getRoles());
        String refreshToken = tokenGenerator.createRefreshToken(customJwtClaim);
        tokenStorage.saveRefreshToken(refreshToken, account.getId());
        return refreshToken;
    }

    public boolean isValidToken(String token) {
        try {
            decodeToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserInfo getUserInfoFromToken(String token) {
        Claims claims = decodeToken(token);
        CustomJwtClaim customJwtClaim = CustomJwtClaim.from(claims);
        return customJwtClaim.toUserInfo();
    }

    private Claims decodeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey.get())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
