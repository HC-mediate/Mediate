package com.ko.mediate.HC.admin.service.dto.response;

import com.ko.mediate.HC.auth.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAccountListDto {
    private List<AdminAccountDto> accounts;
    private boolean hasNext;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    static class AdminAccountDto {
        private String email;
        private String name;
        private String profileUrl;

        static AdminAccountDto fromEntity(Account account) {
            return new AdminAccountDto(
                    account.getEmail(),
                    account.getName(),
                    Objects.isNull(account.getProfileImage())
                            ? null
                            : account.getProfileImage().getProfileUrl());
        }
    }

    public static AdminAccountListDto fromEntities(Slice<Account> contents) {
        return new AdminAccountListDto(
                contents.getContent().stream()
                        .map(AdminAccountDto::fromEntity)
                        .collect(Collectors.toList()),
                contents.hasNext());
    }
}
