package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.auth.resolver.TokenAccountInfo;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
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

  public Tutoring findByTutoringIdWithAuth(long tutoringId, String accountId, RoleType roleType) {
    return tutoringRepository
        .findTutoringByAccountIdAndRole(tutoringId, accountId, roleType)
        .orElseThrow(() -> new MediateNotFoundException("튜터링에 속한 계정이 없습니다."));
  }

  @Transactional
  public TutoringResponseType responseTutoring(
      long tutoringId, TokenAccountInfo token, TutoringResponseDto dto) {
    Tutoring tutoring =
        findByTutoringIdWithAuth(
            tutoringId, token.getAccountId(), RoleType.fromString(token.getAuthority()));

    if (TutoringResponseType.REFUSE == dto.getResponseType()) {
      tutoring.cancelTutoring(RoleType.fromString(token.getAuthority()));
    } else if (TutoringResponseType.ACCEPT == dto.getResponseType()) {
      tutoring.acceptTutoring(RoleType.fromString(token.getAuthority()));
    }
    tutoringRepository.save(tutoring);
    return dto.getResponseType();
  }

  @Transactional
  public void requestTutoring(RequestTutoringDto dto, TokenAccountInfo token) {
    Tutoring tutoring =
        Tutoring.builder()
            .tutoringName(dto.getTutoringName())
            .tutorId(dto.getTutorId())
            .tuteeId(dto.getTuteeId())
            .build();
    tutoring.requestTutoring(RoleType.fromString(token.getAuthority()));
    tutoringRepository.save(tutoring);
  }

  @Transactional
  public boolean cancelTutoring(long tutoringId, TokenAccountInfo token) {
    Tutoring tutoring =
        findByTutoringIdWithAuth(
            tutoringId, token.getAccountId(), RoleType.fromString(token.getAuthority()));
    tutoring.cancelTutoring(RoleType.fromString(token.getAuthority()));
    tutoringRepository.save(tutoring);
    return true;
  }

  @Transactional
  public void updateTutoring(long tutoringId, TokenAccountInfo token, RequestTutoringDto dto) {
    Tutoring tutoring =
        findByTutoringIdWithAuth(
            tutoringId, token.getAccountId(), RoleType.fromString(token.getAuthority()));
    tutoring.changeTutoringName(dto.getTutoringName());
  }
}
