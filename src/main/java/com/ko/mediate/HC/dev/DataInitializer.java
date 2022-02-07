package com.ko.mediate.HC.dev;

import com.ko.mediate.HC.auth.domain.Account;
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
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile(value = {"local"})
public class DataInitializer implements ApplicationRunner {

  private final JpaTutorRepository tutorRepository;
  private final JpaTuteeRepository tuteeRepository;
  private final JpaTutoringRepository tutoringRepository;
  private final JpaAccountRepository accountRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    String tutorId = "tutor", tuteeId = "tutee";
    accountRepository.save(
        new Account(tutorId, tutorId, "010-1234-5678", RoleType.ROLE_TUTOR.name()));
    accountRepository.save(
        new Account(tuteeId, tuteeId, "010-1234-5678", RoleType.ROLE_TUTEE.name()));

    tutorRepository.save(
        new Tutor(
            tutorId, "아무개", "서울시 용산구", Curriculum.HIGH, new AcademicInfo("아무대학교", "아무학과", "3학년")));
    tuteeRepository.save(
        new Tutee(tuteeId, "튜티아무개", "서울시 용산구", new AcademicInfo("아무고등학교", "인문계", "3학년")));

    tutoringRepository.save(
        Tutoring.builder().tutorId(tutorId).tuteeId(tuteeId).tutoringName("수학을 정복하자!").build());
  }
}
