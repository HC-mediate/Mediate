package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.tutoring.application.dto.request.TuteeSignupDto;
import com.ko.mediate.HC.tutoring.application.dto.request.TutorSignupDto;
import com.ko.mediate.HC.tutoring.domain.AccademicInfo;
import com.ko.mediate.HC.tutoring.domain.Account;
import com.ko.mediate.HC.tutoring.domain.AccountId;
import com.ko.mediate.HC.tutoring.domain.Tutee;
import com.ko.mediate.HC.tutoring.domain.Tutor;
import com.ko.mediate.HC.tutoring.infra.JpaAccountRepository;
import com.ko.mediate.HC.tutoring.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutoring.infra.JpaTutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {
  private final JpaAccountRepository accountRepository;
  private final JpaTutorRepository tutorRepository;
  private final JpaTuteeRepository tuteeRepository;
  private final PasswordEncoder passwordEncoder;

  public void isOverlapAccountId(String accountId) {
    if (accountRepository.findByAccountId(new AccountId(accountId)).isPresent()) {
      throw new IllegalArgumentException("이미 ID가 있습니다.");
    }
  }

  public void saveAccount(String id, String rawPassword) {
    Account account = new Account(id, passwordEncoder.encode(rawPassword), "USER");
    accountRepository.save(account);
  }

  @Transactional
  public void tutorJoin(TutorSignupDto dto) {
    isOverlapAccountId(dto.getId());
    saveAccount(dto.getId(), dto.getPassword());
    AccademicInfo info = new AccademicInfo(dto.getSchool(), dto.getMajor(), dto.getGrade());
    Tutor tutor = new Tutor(dto.getId(), dto.getName(), dto.getAddress(), dto.getCurriculum(), info);
    tutorRepository.save(tutor);
  }

  @Transactional
  public void tuteeJoin(TuteeSignupDto dto) {
    isOverlapAccountId(dto.getId());
    saveAccount(dto.getId(), dto.getPassword());
    AccademicInfo info = new AccademicInfo(dto.getSchool(), dto.getGrade());
    Tutee tutee = new Tutee(dto.getName(), dto.getAddress(), info);
    tuteeRepository.save(tutee);
  }
}
