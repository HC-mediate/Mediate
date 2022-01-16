package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.tutoring.application.dto.response.GetTuteeDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutorDto;
import com.ko.mediate.HC.tutoring.domain.AccademicInfo;
import com.ko.mediate.HC.tutoring.domain.Tutee;
import com.ko.mediate.HC.tutoring.domain.Tutor;
import com.ko.mediate.HC.tutoring.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutoring.infra.JpaTutorRepository;
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
}
