package com.ko.mediate.HC.admin.service;

import com.ko.mediate.HC.admin.service.dto.response.AdminAccountListDto;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final JpaAccountRepository accountRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public AdminAccountListDto getAllAccounts(PageRequest pageRequest) {
        return AdminAccountListDto.fromEntities(accountRepository.findAll(pageRequest));
    }
}
