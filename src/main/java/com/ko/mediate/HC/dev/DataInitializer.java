package com.ko.mediate.HC.dev;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.common.domain.DistanceCondition;
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
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
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
  public void run(ApplicationArguments args) {
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
            geometryConverter.convertCoordinateToPoint(123.123, 58.123)));
    tuteeRepository.save(
        new Tutee(
            tuteeId,
            "튜티아무개",
            "서울시 용산구",
            new AcademicInfo("아무고등학교", "인문계", "3학년"),
            geometryConverter.convertCoordinateToPoint(123.123, 58.123)));

    tutoringRepository.save(
        Tutoring.builder().tutorId(tutorId).tuteeId(tuteeId).tutoringName("수학을 정복하자!").build());
    saveTutorSubwayPoint();
  }

  public void saveTutorSubwayPoint() {
    String tutorId = "SubwayTutor";
    HashMap<String, Point> m =
        new HashMap<>() {
          {
            put("Yeong", geometryConverter.convertCoordinateToPoint(126.9052383, 37.5157702));
            put("Shindorim", geometryConverter.convertCoordinateToPoint(126.8890174, 37.5088141));
            put("Darim", geometryConverter.convertCoordinateToPoint(126.8927728, 37.4925085));
            put("Shinchon", geometryConverter.convertCoordinateToPoint(126.9347011, 37.5551399));
            put("Yeouido", geometryConverter.convertCoordinateToPoint(126.9221228, 37.5215737));
            put("Jeju", geometryConverter.convertCoordinateToPoint(126.874237, 33.431441 ));
            put("Incheon", geometryConverter.convertCoordinateToPoint(126.761627, 37.544577));
          }
        };
    for (String id : m.keySet()) {
      accountRepository.save(
          new Account(
              id, passwordEncoder.encode(id), "010-1234-56780", RoleType.ROLE_TUTOR.name()));
    }
    for (String id : m.keySet()) {
      tutorRepository.save(
          new Tutor(
              id,
              id,
              "서울시 용산구",
              Curriculum.HIGH,
              new AcademicInfo("아무대학교", "아무학과", "3학년"),
              m.get(id)));
    }
    List<Tutor> results =
        tutorRepository.findTutorOrderByDistance(PageRequest.of(0, 5), new DistanceCondition(126.9019532, 37.5170112, 5));
    for (Tutor t : results)
      System.out.println(t.getName());
  }
}
