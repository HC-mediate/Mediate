package com.ko.mediate.HC.dev;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.common.domain.GeometryConverter;
import com.ko.mediate.HC.tutee.Infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutor.Infra.JpaTutorRepository;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaAccountRepository;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile(value = {"local", "local-maria"})
public class DataInitializer implements ApplicationRunner {

  private final JpaTutorRepository tutorRepository;
  private final JpaTuteeRepository tuteeRepository;
  private final JpaTutoringRepository tutoringRepository;
  private final JpaAccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final GeometryConverter geometryConverter;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    String tutorId = "tutor", tuteeId = "tutee";
    accountRepository.save(
        new Account(
            tutorId, passwordEncoder.encode(tutorId), "010-1234-5678", RoleType.ROLE_TUTOR.name()));
    accountRepository.save(
        new Account(
            tuteeId, passwordEncoder.encode(tuteeId), "010-1234-5678", RoleType.ROLE_TUTEE.name()));

    tutorRepository.save(
        new Tutor(
            tutorId,
            "아무개",
            "서울시 용산구",
            Curriculum.HIGH,
            new AcademicInfo("아무대학교", "아무학과", "3학년"),
            geometryConverter.convertCoordinateToPoint(123.123, 123.123)));
    tuteeRepository.save(
        new Tutee(
            tuteeId,
            "튜티아무개",
            "서울시 용산구",
            new AcademicInfo("아무고등학교", "인문계", "3학년"),
            geometryConverter.convertCoordinateToPoint(123.123, 123.123)));

    tutoringRepository.save(
        Tutoring.builder().tutorId(tutorId).tuteeId(tuteeId).tutoringName("수학을 정복하자!").build());
    saveTutorsAndTuteesForPaging();
  }

  public void saveTutorsAndTuteesForPaging() throws ParseException {
    String tutorId = "tutor";
    String tuteeId = "tutee";
    for (int i = 0; i < 10; i++) {
      accountRepository.save(
          new Account(
              tutorId + i,
              passwordEncoder.encode(tutorId + i),
              "010-1234-56780",
              RoleType.ROLE_TUTOR.name()));
      accountRepository.save(
          new Account(
              tuteeId + i,
              passwordEncoder.encode(tuteeId + i),
              "010-1234-56780",
              RoleType.ROLE_TUTOR.name()));
    }
    for (int i = 0; i < 10; i++) {
      tutorRepository.save(
          new Tutor(
              tutorId + i,
              "아무개" + i,
              "서울시 용산구",
              Curriculum.HIGH,
              new AcademicInfo("아무대학교", "아무학과", "3학년"),
              geometryConverter.convertCoordinateToPoint(123.123, 123.123)));
      tuteeRepository.save(
          new Tutee(
              tuteeId + i,
              "튜티아무개" + i,
              "서울시 용산구",
              new AcademicInfo("아무고등학교", "인문계", "3학년"),
              geometryConverter.convertCoordinateToPoint(123.123, 123.123)));
    }
  }
}
