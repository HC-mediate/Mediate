package com.ko.mediate.HC.auth.application;

import com.ko.mediate.HC.auth.application.response.GetAccountInfoDto;
import com.ko.mediate.HC.auth.exception.AccountNotFountException;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutee.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutor.infra.JpaTutorRepository;
import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final JpaAccountRepository accountRepository;
  private final JpaTutorRepository tutorRepository;
  private final JpaTuteeRepository tuteeRepository;
  private final PasswordEncoder passwordEncoder;

  public void checkOverlapAccountId(String accountId) {
    if (accountRepository.findByAccountId(accountId).isPresent()) {
      throw new MediateIllegalStateException(String.format("이미 존재하는 ID입니다. [%s]", accountId));
    }
  }

  public void saveAccount(String id, String rawPassword, String name, String phoneNum) {
    checkOverlapAccountId(id);
    Account account =
        Account.builder()
            .accountId(id)
            .password(passwordEncoder.encode(rawPassword))
            .name(name)
            .phoneNum(phoneNum)
            .authority(RoleType.ROLE_USER.name())
            .build();
    accountRepository.save(account);
  }

  public GetAccountInfoDto getAccountInfo(UserInfo token) {
    Account account =
        accountRepository
            .findByAccountId(token.getAccountId())
            .orElseThrow(AccountNotFountException::new);

    if (token.getAuthority().equals(RoleType.ROLE_TUTOR.toString())) {
      Tutor tutor =
          tutorRepository
              .findByAccountId(token.getAccountId())
              .orElseThrow(AccountNotFountException::new);
      return GetAccountInfoDto.fromEntity(account, tutor);
    } else if (token.getAuthority().equals(RoleType.ROLE_TUTEE.toString())) {
      Tutee tutee =
          tuteeRepository
              .findByAccountId(token.getAccountId())
              .orElseThrow(AccountNotFountException::new);
      return GetAccountInfoDto.fromEntity(account, tutee);
    } else {
      return GetAccountInfoDto.fromEntity(account);
    }
  }
}
