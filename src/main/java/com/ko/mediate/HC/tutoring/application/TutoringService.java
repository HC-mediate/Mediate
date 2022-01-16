package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.tutoring.application.dto.response.GetTuteeDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutorDto;
import com.ko.mediate.HC.tutoring.domain.AccademicInfo;
import com.ko.mediate.HC.tutoring.domain.Tutee;
import com.ko.mediate.HC.tutoring.domain.Tutor;
import com.ko.mediate.HC.tutoring.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutoring.infra.JpaTutorRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TutoringService {
  private final JpaTutorRepository tutorRepository;
  private final JpaTuteeRepository tuteeRepository;

  public GetTutorDto getTutorDetail(String accountId) {
    Tutor tutor =
        tutorRepository
            .findByAccountId(accountId)
            .orElseThrow(() -> new IllegalArgumentException("찾으려는 튜터 정보가 없습니다."));
    AccademicInfo info = tutor.getAccademicInfo();
    return new GetTutorDto(
        tutor.getName(), info.getSchool(), info.getGrade(), info.getMajor(), tutor.getAddress());
  }

  public GetTuteeDto getTuteeDetail(String accuntId) {
    Tutee tutee =
        tuteeRepository
            .findByAccountId(accuntId)
            .orElseThrow(() -> new IllegalArgumentException("찾으려는 튜티 정보가 없습니다."));
    AccademicInfo info = tutee.getAccademicInfo();
    return new GetTuteeDto(tutee.getName(), info.getSchool(), info.getGrade(), tutee.getAddress());
  }

  public List<GetTutorDto> getAllTutor() {
    return tutorRepository.findAll().stream()
        .map(
            t -> {
              AccademicInfo info = t.getAccademicInfo();
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
              AccademicInfo info = t.getAccademicInfo();
              GetTuteeDto dto =
                  new GetTuteeDto(t.getName(), info.getSchool(), info.getGrade(), t.getAddress());
              return dto;
            })
        .collect(Collectors.toList());
  }
}
