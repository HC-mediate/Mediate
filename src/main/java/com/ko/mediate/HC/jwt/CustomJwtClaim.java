package com.ko.mediate.HC.jwt;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.tutoring.application.RoleType;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomJwtClaim {

    private final Map<String, String> contents = new HashMap<>();

    private static final String ID = "id";

    private static final String EMAIL = "email";

    private static final String NICKNAME = "nickname";

    private static final String ROLE = "role";

    public Map<String, String> getContents() {
        return this.contents;
    }

    public UserInfo toUserInfo() {
        return UserInfo.builder()
                .accountId(Long.valueOf(this.contents.get(ID)))
                .accountEmail(this.contents.get(EMAIL))
                .accountNickname(this.contents.get(NICKNAME))
                .roles(this.contents.get(ROLE))
                .build();
    }

    public static CustomJwtClaim of(Long id, String email, String nickname, List<RoleType> roles) {
        CustomJwtClaim customJwtClaim = new CustomJwtClaim();
        customJwtClaim.contents.put(ID, id.toString());
        customJwtClaim.contents.put(EMAIL, email);
        customJwtClaim.contents.put(NICKNAME, nickname);
        customJwtClaim.contents.put(ROLE,
                roles.stream().map(RoleType::toString).collect(Collectors.joining(",")));
        return customJwtClaim;
    }

    public static CustomJwtClaim from(Claims claims) {
        CustomJwtClaim customJwtClaim = new CustomJwtClaim();
        customJwtClaim.contents.put(ID, claims.get(ID).toString());
        customJwtClaim.contents.put(EMAIL, claims.get(EMAIL).toString());
        customJwtClaim.contents.put(NICKNAME, claims.get(NICKNAME).toString());
        customJwtClaim.contents.put(ROLE, claims.get(ROLE).toString());
        return customJwtClaim;
    }
}
