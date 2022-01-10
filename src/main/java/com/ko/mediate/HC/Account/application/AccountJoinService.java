package com.ko.mediate.HC.Account.application;

import com.ko.mediate.HC.Account.application.dto.request.SignupDto;
import com.ko.mediate.HC.Account.domain.Account;
import java.util.Arrays;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountJoinService {
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public void Join(SignupDto dto) {
    if(accountRepository.findByAccountId(dto.getId()).isPresent()){
      throw new IllegalArgumentException("이미 ID가 있습니다.");
    }
    HashSet<String> authority = new HashSet<>(Arrays.asList("USER"));
    Account newAccount = new Account(dto.getId(),     passwordEncoder.encode(dto.getPassword()), dto.getPhoneNum(), authority);
    accountRepository.save(newAccount);
  }
}
