package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.tutor.Infra.JpaTutorRepository;
import com.ko.mediate.HC.tutoring.domain.Account;
import com.ko.mediate.HC.tutoring.domain.AccountId;
import com.ko.mediate.HC.tutoring.infra.JpaAccountRepository;
import com.ko.mediate.HC.tutee.Infra.JpaTuteeRepository;
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

  public void isOverlapAccountId(String accountId) {
    if (accountRepository.findByAccountId(new AccountId(accountId)).isPresent()) {
      throw new IllegalArgumentException("이미 ID가 있습니다.");
    }
  }

  public void saveAccount(String id, String rawPassword, String phoneNum, RoleType roleType) {
    Account account =
        Account.builder()
            .accountId(id)
            .password(passwordEncoder.encode(rawPassword))
            .phoneNum(phoneNum)
            .authority(roleType.name())
            .build();
    accountRepository.save(account);
  }


}
