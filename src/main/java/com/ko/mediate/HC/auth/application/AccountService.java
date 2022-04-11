package com.ko.mediate.HC.auth.application;

import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.domain.AccountId;
import com.ko.mediate.HC.tutoring.infra.JpaAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final JpaAccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public void checkOverlapAccountId(String accountId) {
    if (accountRepository.findByAccountId(new AccountId(accountId)).isPresent()) {
      throw new MediateIllegalStateException(String.format("이미 존재하는 ID입니다. [%s]", accountId));
    }
  }

  public void saveAccount(
      String id, String rawPassword, String name, String phoneNum, RoleType roleType) {
    checkOverlapAccountId(id);
    Account account =
        Account.builder()
            .accountId(id)
            .password(passwordEncoder.encode(rawPassword))
            .name(name)
            .phoneNum(phoneNum)
            .authority(roleType.name())
            .build();
    accountRepository.save(account);
  }
}
