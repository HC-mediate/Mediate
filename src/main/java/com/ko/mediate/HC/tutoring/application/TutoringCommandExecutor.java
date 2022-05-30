package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.CommonResponseDto;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutoring.application.request.RequestProgressDto;
import com.ko.mediate.HC.tutoring.application.request.TutoringResponseDto;
import com.ko.mediate.HC.tutoring.application.request.RequestTutoringDto;
import com.ko.mediate.HC.tutoring.application.request.TutoringResponseType;
import com.ko.mediate.HC.tutoring.domain.Progress;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutoringCommandExecutor {
  private final JpaTutoringRepository tutoringRepository;

  public Tutoring findByTutoringIdWithAuth(long tutoringId, UserInfo userInfo, RoleType roleType) {
    return tutoringRepository
        .findTutoringByAccountIdAndRole(tutoringId, userInfo.getAccountEmail(), roleType)
        .orElseThrow(() -> new MediateNotFoundException("튜터링에 속한 계정이 없습니다."));
  }

  private Tutoring findByTutoringIdWithDetail(long tutoringId) {
    return tutoringRepository
        .findByTutoringIdWithDetail(tutoringId)
        .orElseThrow(() -> new MediateNotFoundException("ID가 없습니다."));
  }

  @Transactional
  public CommonResponseDto responseTutoring(
      long tutoringId, UserInfo userInfo, TutoringResponseDto dto) {
    Tutoring tutoring = findByTutoringIdWithAuth(tutoringId, userInfo, userInfo.getRole());

    if (TutoringResponseType.REFUSE == dto.getResponseType()) {
      tutoring.cancelTutoring(userInfo.getRole());
    } else if (TutoringResponseType.ACCEPT == dto.getResponseType()) {
      tutoring.acceptTutoring(userInfo.getRole());
    }
    tutoringRepository.save(tutoring);
    return new CommonResponseDto("튜터링이 " + dto.getResponseType().getMessage() + " 되었습니다.");
  }

  @Transactional
  public void requestTutoring(RequestTutoringDto dto, UserInfo userInfo) {
    Tutoring tutoring =
        Tutoring.builder()
            .tutoringName(dto.getTutoringName())
            .tutorEmail(dto.getTutorEmail())
            .tuteeEmail(dto.getTuteeEmail())
            .build();
    tutoring.requestTutoring(userInfo.getRole());
    tutoringRepository.save(tutoring);
  }

  @Transactional
  public boolean cancelTutoring(long tutoringId, UserInfo userInfo) {
    Tutoring tutoring = findByTutoringIdWithAuth(tutoringId, userInfo, userInfo.getRole());
    tutoring.cancelTutoring(userInfo.getRole());
    tutoringRepository.save(tutoring);
    return true;
  }

  @Transactional
  public void updateTutoring(long tutoringId, UserInfo userInfo, RequestTutoringDto dto) {
    Tutoring tutoring = findByTutoringIdWithAuth(tutoringId, userInfo, userInfo.getRole());
    tutoring.changeTutoringName(dto.getTutoringName());
  }

  @Transactional
  public void addProgressInTutoring(long tutoringId, RequestProgressDto dto) {
    Tutoring tutoring = findByTutoringIdWithDetail(tutoringId);
    tutoring.addProgress(new Progress(dto.getWeek(), dto.getContent(), dto.getIsCompleted()));
    tutoringRepository.save(tutoring);
  }

  @Transactional
  public void modifyProgressInTutoring(long tutoringId, long progressId, RequestProgressDto dto) {
    Tutoring tutoring = findByTutoringIdWithDetail(tutoringId);
    tutoring.modifyProgress(progressId, dto.getWeek(), dto.getContent(), dto.getIsCompleted());
  }

  @Transactional
  public void removeProgressInTutoring(long tutoringId, long progressId) {
    Tutoring tutoring = findByTutoringIdWithDetail(tutoringId);
    tutoring.removeProgress(progressId);
  }
}
