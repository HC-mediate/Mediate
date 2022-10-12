package com.ko.mediate.HC.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenGenerator {

    @Value("${jwt.access.validity-period}")
    private long accessTokenValidityPeriodInMillis;

    @Value("${jwt.refresh.validity-period}")
    private long refreshTokenValidityPeriodInMillis;

    private final JwtSecretKey jwtSecretKey;

    public String createAccessToken(CustomJwtClaim customJwtClaim) {
        return createToken(customJwtClaim, this.accessTokenValidityPeriodInMillis);
    }

    public String createRefreshToken(CustomJwtClaim customJwtClaim) {
        return createToken(customJwtClaim, this.refreshTokenValidityPeriodInMillis);
    }

    private String createToken(CustomJwtClaim customJwtClaim, long tokenValidityPeriodInMillis) {
        return Jwts.builder()
                .setClaims(customJwtClaim.getContents())
                .signWith(jwtSecretKey.get(), SignatureAlgorithm.HS512)
                .setExpiration(getExpiration(tokenValidityPeriodInMillis))
                .compact();
    }

    private Date getExpiration(long validityPeriodInMillis) {
        return new Date(System.currentTimeMillis() + validityPeriodInMillis);
    }
}
