package com.ko.mediate.HC.tutee.application;

import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutee.application.response.GetTuteeAccountDto;
import com.ko.mediate.HC.tutee.application.response.GetTuteeDto;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.tutee.Infra.JpaTuteeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TuteeQueryProcessor {
  private JpaTuteeRepository tuteeRepository;

  public List<GetTuteeDto> getAllTutee() {
    return tuteeRepository.findAll().stream()
        .map(
            t -> {
              AcademicInfo info = t.getAcademicInfo();
              GetTuteeDto dto =
                  new GetTuteeDto(t.getName(), info.getSchool(), info.getGrade(), t.getAddress());
              return dto;
            })
        .collect(Collectors.toList());
  }

  public GetTuteeDto getTuteeDetail(String accountId) {
    Tutee tutee =
        tuteeRepository
            .findByAccountId(accountId)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID를 찾을 수 없습니다."));
    AcademicInfo info = tutee.getAcademicInfo();
    return new GetTuteeDto(tutee.getName(), info.getSchool(), info.getGrade(), tutee.getAddress());
  }

  public GetTuteeAccountDto getTuteeAccount(String accountId) {
    return tuteeRepository.findTuteeAccountInfoById(accountId).stream()
        .map(
            o -> {
              Tutee t = (Tutee) o[0];
              Account a = (Account) o[1];
              return new GetTuteeAccountDto(
                  a.getStringAccountId(),
                  a.getPhoneNum(),
                  t.getName(),
                  t.getAcademicInfo().getSchool(),
                  t.getAcademicInfo().getGrade(),
                  t.getAddress());
            })
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("찾는 ID가 없습니다."));
  }
}
