package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseDto;
import com.ko.mediate.HC.tutoring.application.dto.request.RequestTutoringDto;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseType;
import com.ko.mediate.HC.tutoring.application.dto.response.GetHomeworkDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetProgressDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDetailDto;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutoringCommandExecutor {
  private final JpaTutoringRepository tutoringRepository;
  private final TokenProvider tokenProvider;

  public Tutoring findTutoringWithAuthAndId(String authValue, long tutoringId) {
    return tutoringRepository
        .findByIdWithAccountId(tutoringId, tokenProvider.getAccountIdWithToken(authValue))
        .orElseThrow(() -> new IllegalArgumentException("튜터링에 접근 권한이 없습니다."));
  }

  @Transactional
  public long requestTutoring(String authValue, RequestTutoringDto dto) {
    tokenProvider.authenticateByAccountId(authValue, dto.getFromId());
    Tutoring tutoring = new Tutoring(dto.getFromId(), dto.getToId());
    return tutoringRepository.save(tutoring).getId();
  }

  @Transactional
  public TutoringResponseType TutoringResponse(
      String authValue, long tutoringId, TutoringResponseDto dto) {
    Tutoring tutoring = findTutoringWithAuthAndId(authValue, tutoringId);

    if (dto.getResponseType() == TutoringResponseType.ACCEPT) {
      tutoring.acceptTutoring();
      return TutoringResponseType.ACCEPT;
    } else {
      tutoringRepository.delete(tutoring);
      return TutoringResponseType.REFUSE;
    }
  }

  @Transactional
  public boolean cancelTutoring(String authValue, long tutoringId) {
    Tutoring tutoring = findTutoringWithAuthAndId(authValue, tutoringId);
    return tutoring.cancelTutoring();
  }

  @Transactional(readOnly = true)
  public GetTutoringDetailDto getTutoringDetail(long tutoringId) {
    Tutoring tutoring = tutoringRepository.findTutoringDetailInfoById(tutoringId)
        .orElseThrow(() -> new IllegalArgumentException("No Such Id"));
    return new GetTutoringDetailDto(tutoring.getTutoringName(),
        LocalDateTime.parse(tutoring.getStartedAt(), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        tutoring.getHomeworks().stream().map(
            h -> {return new GetHomeworkDto(h.getId(), h.getContent(), h.getIsFinished());}
        ).collect(Collectors.toList()),
        tutoring.getProgresses().stream().map(
            p -> {return new GetProgressDto(p.getId(), p.getContent(),p.getIsFinished());}
        ).collect(Collectors.toList()));
  }
}
