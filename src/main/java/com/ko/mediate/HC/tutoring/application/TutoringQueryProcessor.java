package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.tutoring.application.dto.response.GetHomeworkDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetProgressDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTuteeAccountDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTuteeDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutorAccountDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutorDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDetailDto;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutoring.domain.Account;
import com.ko.mediate.HC.tutoring.domain.AccountId;
import com.ko.mediate.HC.tutoring.domain.Tutee;
import com.ko.mediate.HC.tutoring.domain.Tutor;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutoring.infra.JpaTutorRepository;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutoringQueryProcessor {
  private final JpaTutorRepository tutorRepository;
  private final JpaTuteeRepository tuteeRepository;
  private final JpaTutoringRepository tutoringRepository;

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

  public GetTutorDto getTutorDetail(String accountId) {
    Tutor tutor =
        tutorRepository
            .findByAccountId(accountId)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 찾을 수 없습니다."));
    AcademicInfo info = tutor.getAcademicInfo();
    return new GetTutorDto(
        tutor.getName(), info.getSchool(), info.getMajor(), info.getGrade(), tutor.getAddress());
  }

  public GetTuteeDto getTuteeDetail(String accountId) {
    Tutee tutee =
        tuteeRepository
            .findByAccountId(accountId)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID를 찾을 수 없습니다."));
    AcademicInfo info = tutee.getAcademicInfo();
    return new GetTuteeDto(tutee.getName(), info.getSchool(), info.getGrade(), tutee.getAddress());
  }

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

  public GetTutoringDetailDto getTutoringDetailById(long tutoringId) {
    Tutoring tutoring =
        tutoringRepository
            .findTutoringDetailInfoById(tutoringId)
            .orElseThrow(() -> new IllegalArgumentException("No Such Id"));
    return new GetTutoringDetailDto(
        tutoring.getTutoringName(),
        tutoring.getStartedAt(),
        tutoring.getHomeworks().stream()
            .map(
                h -> {
                  return new GetHomeworkDto(h.getId(), h.getContent(), h.getIsFinished());
                })
            .collect(Collectors.toList()),
        tutoring.getProgresses().stream()
            .map(
                p -> {
                  return new GetProgressDto(p.getId(), p.getContent(), p.getIsFinished());
                })
            .collect(Collectors.toList()));
  }
}
