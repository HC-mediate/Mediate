package com.ko.mediate.HC.tutor.application;

import com.ko.mediate.HC.tutor.Infra.JpaTutorRepository;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutor.application.response.GetTutorAccountDto;
import com.ko.mediate.HC.tutor.application.response.GetTutorDto;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutoring.domain.Account;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutorQueryProcessor {
  private final JpaTutorRepository tutorRepository;
  public List<GetTutorDto> getAllTutor() {
    return tutorRepository.findAll().stream()
        .map(
            t -> {
              AcademicInfo info = t.getAcademicInfo();
              GetTutorDto dto =
                  new GetTutorDto(
                      t.getName(),
                      info.getSchool(),
                      info.getMajor(),
                      info.getGrade(),
                      t.getAddress());
              return dto;
            })
        .collect(Collectors.toList());
  }

  public GetTutorDto getTutorDetail(String accountId) {
    Tutor tutor =
        tutorRepository
            .findByAccountId(accountId)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 찾을 수 없습니다."));
    AcademicInfo info = tutor.getAcademicInfo();
    return new GetTutorDto(
        tutor.getName(), info.getSchool(), info.getMajor(), info.getGrade(), tutor.getAddress());
  }

  public GetTutorAccountDto getTutorAccount(String accountId) {
    return tutorRepository.findTutorAccountInfoById(accountId).stream()
        .map(
            o -> {
              Tutor t = (Tutor) o[0];
              Account a = (Account) o[1];
              return new GetTutorAccountDto(
                  a.getStringAccountId(),
                  a.getPhoneNum(),
                  t.getName(),
                  t.getAcademicInfo().getSchool(),
                  t.getAcademicInfo().getGrade(),
                  t.getAcademicInfo().getMajor(),
                  t.getAddress());
            })
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("찾는 ID가 없습니다."));
  }
}
