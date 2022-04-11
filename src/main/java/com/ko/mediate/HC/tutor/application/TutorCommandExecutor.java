package com.ko.mediate.HC.tutor.application;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.domain.AccountId;
import com.ko.mediate.HC.common.domain.GeometryConverter;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutor.Infra.JpaTutorRepository;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutor.application.request.TutorSignupDto;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutoring.infra.JpaAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutorCommandExecutor {
  private final JpaTutorRepository tutorRepository;
  private final JpaAccountRepository accountRepository;
  private final GeometryConverter geometryConverter;

  @Transactional
  public void tutorJoin(TutorSignupDto dto) {
    AcademicInfo info = new AcademicInfo(dto.getSchool(), dto.getMajor(), dto.getGrade());
    Tutor tutor =
        new Tutor(
            dto.getAccountId(),
            dto.getName(),
            dto.getAddress(),
            dto.getCurriculum(),
            info,
            geometryConverter.convertCoordinateToPoint(dto.getLatitude(), dto.getLongitude()));
    tutorRepository.save(tutor);

    Account account =
        accountRepository
            .findByAccountId(new AccountId(dto.getAccountId()))
            .orElseThrow(() -> new MediateNotFoundException("ID가 없습니다."));
    account.joinTutor();
  }
}
