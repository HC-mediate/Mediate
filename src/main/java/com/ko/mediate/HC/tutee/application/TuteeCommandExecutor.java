package com.ko.mediate.HC.tutee.application;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.domain.GeometryConverter;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutee.application.request.TuteeSignupDto;
import com.ko.mediate.HC.tutee.infra.JpaTuteeRepository;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TuteeCommandExecutor {
  private final JpaTuteeRepository tuteeRepository;
  private final GeometryConverter geometryConverter;
  private final JpaAccountRepository accountRepository;

  private Account findAccountByEmail(UserInfo userInfo) {
    return accountRepository
        .findById(userInfo.getAccountId())
        .orElseThrow(MediateNotFoundException::new);
  }

  @Transactional
  public void tuteeJoin(UserInfo userInfo, TuteeSignupDto dto) {
    Account account = findAccountByEmail(userInfo);
    account.joinTutee();

    Tutee tutee =
        Tutee.builder()
            .account(account)
            .school(dto.getSchool())
            .major(dto.getMajor())
            .grade(dto.getGrade())
            .address(dto.getAddress())
            .location(
                geometryConverter.convertCoordinateToPoint(dto.getLatitude(), dto.getLongitude()))
            .build();
    tuteeRepository.save(tutee);
  }
}
