package com.ko.mediate.HC.auth.application;

import com.ko.mediate.HC.auth.application.request.SignUpDto;
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

  public void checkExistEmail(String email) {
    if (accountRepository.existsByEmail(email)) {
      throw new MediateIllegalStateException(String.format("이미 존재하는 email입니다. [%s]", email));
    }
  }

  public void saveAccount(SignUpDto dto) {
    checkExistEmail(dto.getEmail());
    Account account =
        Account.builder()
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .name(dto.getName())
            .phoneNum(dto.getPhoneNum())
            .authority(RoleType.ROLE_USER.name())
            .build();
    accountRepository.save(account);
  }

  public GetAccountInfoDto getAccountInfo(UserInfo userInfo) {
    if (userInfo.getCurrentRole() == RoleType.ROLE_TUTOR) {
      Tutor tutor =
          tutorRepository
              .findTutorByAccountEmail(userInfo.getAccountEmail())
              .orElseThrow(AccountNotFountException::new);
      return GetAccountInfoDto.fromEntity(tutor);
    } else if (userInfo.getCurrentRole() == RoleType.ROLE_TUTEE) {
      Tutee tutee =
          tuteeRepository
              .findByAccountEmail(userInfo.getAccountEmail())
              .orElseThrow(AccountNotFountException::new);
      return GetAccountInfoDto.fromEntity(tutee);
    } else {
      Account account =
          accountRepository
              .findById(userInfo.getAccountId())
              .orElseThrow(AccountNotFountException::new);
      return GetAccountInfoDto.fromEntity(account);
    }
  }
}
