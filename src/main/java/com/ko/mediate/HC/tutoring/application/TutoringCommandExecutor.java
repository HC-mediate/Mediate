package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseDto;
import com.ko.mediate.HC.tutoring.application.dto.request.RequestTutoringDto;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseType;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutoringCommandExecutor {
  private final JpaTutoringRepository tutoringRepository;
  private final TokenProvider tokenProvider;

  @Transactional
  public long requestTutoring(String authValue, RequestTutoringDto dto) {
    tokenProvider.authenticateByAccountId(authValue, dto.getFromId());
    Tutoring tutoring = new Tutoring(dto.getFromId(), dto.getToId());
    return tutoringRepository.save(tutoring).getId();
  }

  @Transactional
  public TutoringResponseType TutoringResponse(
      String authValue, long tutoringId, TutoringResponseDto dto) {
    tokenProvider.authenticateByAccountId(authValue, dto.getAccountId());
    Tutoring tutoring =
        tutoringRepository
            .findById(tutoringId)
            .orElseThrow(() -> new IllegalArgumentException("생성되지 않은 튜터링입니다."));

    if (dto.getType() == TutoringResponseType.ACCEPT) {
      tutoring.acceptTutoring();
      return TutoringResponseType.ACCEPT;
    }
    else {
      tutoringRepository.delete(tutoring);
      return TutoringResponseType.REFUSE;
    }
  }
}
