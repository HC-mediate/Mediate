package com.ko.mediate.HC.auth.application;

import com.ko.mediate.HC.auth.application.dto.request.SignUpDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateAlreadyExistException;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.tutoring.application.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final JpaAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private void checkExistEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new MediateIllegalStateException(ErrorCode.EMAIL_ALREADY_EXIST);
        }
    }

    private void checkExistNickname(String nickname) {
        if (accountRepository.existsByNickname(nickname)) {
            throw new MediateIllegalStateException(ErrorCode.NICKNAME_ALREADY_EXIST);
        }
    }

    public void saveAccount(SignUpDto dto) {
        checkExistEmail(dto.getEmail());
        checkExistNickname(dto.getNickname());
        Account account =
                Account.builder()
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .nickname(dto.getNickname())
                        .phoneNum(dto.getPhoneNum())
                        .role(RoleType.ROLE_USER)
                        .build();
        accountRepository.save(account);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository
                .findAccountByEmail(email)
                .orElseThrow(() -> new MediateAlreadyExistException(ErrorCode.EMAIL_ALREADY_EXIST));
    }
}
