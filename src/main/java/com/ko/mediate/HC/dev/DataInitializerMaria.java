package com.ko.mediate.HC.dev;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.domain.GeometryConverter;
import com.ko.mediate.HC.facade.query.DistanceQueryFacade;
import com.ko.mediate.HC.tutee.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutor.application.TutorCommandExecutor;
import com.ko.mediate.HC.tutor.application.TutorQueryProcessor;
import com.ko.mediate.HC.tutor.application.request.TutorSignupDto;
import com.ko.mediate.HC.tutor.application.response.GetTutorListDto;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutor.infra.JpaTutorRepository;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile(value = {"local-maria"})
public class DataInitializerMaria implements ApplicationRunner {

  private final JpaTutorRepository tutorRepository;
  private final JpaTuteeRepository tuteeRepository;
  private final JpaAccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final GeometryConverter geometryConverter;
  private final DistanceQueryFacade distanceQueryFacade;

  Account createAccount(String email, String name, double longitude, double latitude) {
    Account account = Account.builder()
        .email(email)
        .password(passwordEncoder.encode("1234"))
        .name(name)
        .role(RoleType.ROLE_USER)
        .build();
    account.joinTutor(geometryConverter.convertCoordinateToPoint(latitude, longitude));
    return account;
  }

  UserInfo createUserInfo(Account account) {
    return new UserInfo(account.getId(), account.getEmail(), RoleType.ROLE_USER.name());
  }

  TutorSignupDto createTutorSignupDto(String school, List<Curriculum> curriculums) {
    return new TutorSignupDto(school, null, null, null, curriculums, null);
  }

  Tutor createTutor(Account account, String school, List<Curriculum> curriculums){
    return Tutor.builder().account(account)
      .school(school)
      .curriculums(curriculums).build();
  }

  @Override
  public void run(ApplicationArguments args) {
    List<Account> accounts =
        accountRepository.saveAllAndFlush(
            List.of(
              createAccount("Timesquare", "타임스퀘어", 126.9019532, 37.5170112),
                createAccount("Yeong", "영등포", 126.9052383, 37.5157702),
                createAccount("Shindorim", "신도림", 126.8890174, 37.5088141),
                createAccount("Darim", "대림", 126.8927728, 37.4925085),
                createAccount("Shinchon", "신촌", 126.9347011, 37.5551399),
                createAccount("Yeouido", "여의도", 126.9221228, 37.5215737),
                createAccount("Jeju", "제주", 126.874237, 33.431441),
                createAccount("Incheon", "인천", 126.761627, 37.544577)));

    for(Account account: accounts){
      tutorRepository.save(createTutor(account, account.getName(), List.of(Curriculum.ELEMENT, Curriculum.MIDDLE)));
    }

    GetTutorListDto results = distanceQueryFacade.getAllTutorByDistance(
      createUserInfo(accounts.get(0)), PageRequest.of(0, 10), 3);
  }
}
