package com.ko.mediate.HC.tutee.application;

import com.ko.mediate.HC.common.domain.GeometryConverter;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.auth.application.AccountService;
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
  private final GeometryConverter geometryConverter;

  @Transactional
  public void tuteeJoin(TuteeSignupDto dto) {
    AcademicInfo info = new AcademicInfo(dto.getSchool(), dto.getGrade());
    Tutee tutee =
        new Tutee(
            dto.getAccountId(),
            dto.getName(),
            dto.getAddress(),
            info,
            geometryConverter.convertCoordinateToPoint(dto.getLatitude(), dto.getLongitude()));
    tuteeRepository.save(tutee);
  }
}
