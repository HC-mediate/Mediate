package com.ko.mediate.HC.auth.resolver;

import com.ko.mediate.HC.tutoring.application.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class UserInfo {
    private Long accountId;
    private String accountEmail;
    private List<RoleType> roles;

    public boolean hasRole(RoleType role) {
        return roles.contains(role);
    }

    public UserInfo(Long accountId, String accountEmail, String roles) {
        this.accountId = accountId;
        this.accountEmail = accountEmail;
        this.roles =
                Arrays.stream(roles.split(",")).map(RoleType::fromString).collect(Collectors.toList());
    }
}
