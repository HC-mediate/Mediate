package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseDto;
import com.ko.mediate.HC.tutoring.application.dto.request.RequestTutoringDto;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseType;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringCustomRepository;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutoringCommandExecutor {
  private final JpaTutoringRepository tutoringRepository;
  private final JpaTutoringCustomRepository tutoringCustomRepository;
  private final TokenProvider tokenProvider;

  public Tutoring findByTutoringId(long tutoringId) {
    return tutoringRepository
        .findById(tutoringId)
        .orElseThrow(() -> new MediateNotFoundException("찾는 ID가 없습니다."));
  }

  @Transactional
  public TutoringResponseType responseTutoring(long tutoringId, String authValue, TutoringResponseDto dto) {
    String accountId = tokenProvider.getAccountIdWithToken(authValue);
    RoleType roleType = tokenProvider.getRoleWithToken(authValue);
    Tutoring tutoring = tutoringCustomRepository.findTutoringByAccountIdAndRole(tutoringId, accountId, roleType)
        .orElseThrow(() -> new MediateNotFoundException("튜터링에 속한 계정이 없습니다."));
    if(TutoringResponseType.REFUSE == dto.getResponseType()){
      tutoring.cancelTutoring();
    }
    else if(TutoringResponseType.ACCEPT == dto.getResponseType()){
      tutoring.acceptTutoring();
    }
    tutoringRepository.save(tutoring);
    return dto.getResponseType();
  }

  @Transactional
  public void requestTutoring(RequestTutoringDto dto) {
    Tutoring tutoring =
        Tutoring.builder()
            .tutoringName(dto.getTutoringName())
            .tutorId(dto.getTutorId())
            .tuteeId(dto.getTuteeId())
            .build();
    tutoringRepository.save(tutoring);
  }

  @Transactional
  public boolean cancelTutoring(long tutoringId) {
    Tutoring tutoring = findByTutoringId(tutoringId);
    tutoring.cancelTutoring();
    tutoringRepository.save(tutoring);
    return true;
  }

  @Transactional
  public void updateTutoring(long tutoringId, RequestTutoringDto dto) {
    Tutoring tutoring = findByTutoringId(tutoringId);
    tutoring.changeTutoringName(dto.getTutoringName());
  }
}
