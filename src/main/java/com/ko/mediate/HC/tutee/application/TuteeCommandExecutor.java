package com.ko.mediate.HC.tutee.application;

import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutoring.application.AccountService;
import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.tutee.application.request.TuteeSignupDto;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutee.Infra.JpaTuteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TuteeCommandExecutor {
  private final JpaTuteeRepository tuteeRepository;
  private final AccountService accountService;

  @Transactional
  public void tuteeJoin(TuteeSignupDto dto) {
    accountService.isOverlapAccountId(dto.getId());
    accountService.saveAccount(dto.getId(), dto.getPassword(), dto.getPhoneNum(), RoleType.ROLE_TUTEE);
    AcademicInfo info = new AcademicInfo(dto.getSchool(), dto.getGrade());
    Tutee tutee = new Tutee(dto.getId(), dto.getName(), dto.getAddress(), info);
    tuteeRepository.save(tutee);
  }
}
