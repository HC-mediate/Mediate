package com.ko.mediate.HC.tutor.application;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.domain.DistanceCondition;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutee.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutor.infra.JpaTutorRepository;
import com.ko.mediate.HC.tutor.application.response.GetTutorListDto;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutor.application.response.GetTutorAccountDto;
import com.ko.mediate.HC.tutor.application.response.GetTutorDto;
import com.ko.mediate.HC.tutoring.application.RoleType;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.auth.domain.Account;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutorQueryProcessor {
  private final JpaTutorRepository tutorRepository;
  private final JpaTuteeRepository tuteeRepository;

  public DistanceCondition initSearchCondition(UserInfo userInfo, int radius) {
    if (userInfo.getRole() == RoleType.ROLE_TUTOR) {
      Tutor tutor =
          tutorRepository
              .findTutorByAccountEmail(userInfo.getAccountEmail())
              .orElseThrow(MediateNotFoundException::new);
      return new DistanceCondition(tutor.getLocation().getX(), tutor.getLocation().getY(), radius);
    } else if (userInfo.getRole() == RoleType.ROLE_TUTEE) {
      Tutee tutee =
          tuteeRepository
              .findByAccountEmail(userInfo.getAccountEmail())
              .orElseThrow(MediateNotFoundException::new);
      return new DistanceCondition(tutee.getLocation().getX(), tutee.getLocation().getY(), radius);
    } else {
      throw new MediateIllegalStateException("튜터/튜티 위치 정보가 등록된 유저만 해당 기능을 사용할 수 있습니다.");
    }
  }

  public GetTutorListDto getAllTutorByDistance(
      UserInfo userInfo, PageRequest pageRequest, int radius) {
    DistanceCondition condition = initSearchCondition(userInfo, radius);
    return GetTutorListDto.fromEntities(
        tutorRepository.findAllTutorOrderByDistance(pageRequest, condition));
  }

  public GetTutorDto getTutorDetail(Long tutorId) {
    return GetTutorDto.fromEntity(
        tutorRepository.findByIdFetchAccount(tutorId).orElseThrow(MediateNotFoundException::new));
  }
}
