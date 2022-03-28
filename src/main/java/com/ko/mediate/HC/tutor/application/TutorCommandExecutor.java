package com.ko.mediate.HC.tutor.application;

import com.ko.mediate.HC.common.domain.GeometryConverter;
import com.ko.mediate.HC.tutor.Infra.JpaTutorRepository;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.tutor.application.request.TutorSignupDto;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutorCommandExecutor {
  private final JpaTutorRepository tutorRepository;
  private final AccountService accountService;
  private final GeometryConverter geometryConverter;

  @Transactional
  public void tutorJoin(TutorSignupDto dto) {
    accountService.isOverlapAccountId(dto.getId());
    accountService.saveAccount(
        dto.getId(), dto.getPassword(), dto.getPhoneNum(), RoleType.ROLE_TUTOR);
    AcademicInfo info = new AcademicInfo(dto.getSchool(), dto.getMajor(), dto.getGrade());
    Tutor tutor =
        new Tutor(
            dto.getId(),
            dto.getName(),
            dto.getAddress(),
            dto.getCurriculum(),
            info,
            geometryConverter.convertCoordinateToPoint(dto.getLatitude(), dto.getLongitude()));
    tutorRepository.save(tutor);
  }
}
