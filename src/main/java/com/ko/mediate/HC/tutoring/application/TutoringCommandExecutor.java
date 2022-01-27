package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseDto;
import com.ko.mediate.HC.tutoring.application.dto.request.RequestTutoringDto;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseType;
import com.ko.mediate.HC.tutoring.application.dto.response.GetHomeworkDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetProgressDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDetailDto;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.domain.TutoringStat;
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

  @Transactional
  public long requestTutoring(String authValue, RequestTutoringDto dto) {
    Tutoring tutoring =
        Tutoring.builder()
            .tutoringName(dto.getTutoringName())
            .tutorId(dto.getTutorId())
            .tuteeId(dto.getTuteeId())
            .stat(TutoringStat.WAITING_ACCEPT)
            .build();
    return tutoringRepository.save(tutoring).getId();
  }

  @Transactional
  public TutoringResponseType responseTutoring(
      String authValue, long tutoringId, TutoringResponseDto dto) {

    Tutoring tutoring =
        tutoringRepository
            .findById(tutoringId)
            .orElseThrow(() -> new IllegalArgumentException("No Such Id"));

    if (dto.getResponseType() == TutoringResponseType.ACCEPT) {
      tutoring.acceptTutoring();
      return TutoringResponseType.ACCEPT;
    }
    else if(tutoring.isWaitingAcceptStat()){
      tutoringRepository.delete(tutoring);
      return TutoringResponseType.REFUSE;
    }
    else{
      throw new IllegalArgumentException("수락 대기 중 상태가 아닙니다.");
    }
  }

  @Transactional
  public void requestTutoring(RequestTutoringDto dto) {
    Tutoring tutoring =
        Tutoring.builder()
            .tutoringName(dto.getTutoringName())
            .tutorId(dto.getTutorId())
            .tuteeId(dto.getTuteeId())
            .stat(TutoringStat.WAITING_ACCEPT)
            .build();
    tutoringRepository.save(tutoring);
  }

  @Transactional
  public boolean cancelTutoring(String authValue, long tutoringId) {
    Tutoring tutoring =
        tutoringRepository
            .findById(tutoringId)
            .orElseThrow(() -> new IllegalArgumentException("No Such Id"));
    return tutoring.cancelTutoring();
  }

  @Transactional(readOnly = true)
  public GetTutoringDetailDto getTutoringDetail(long tutoringId) {
    Tutoring tutoring =
        tutoringRepository
            .findTutoringDetailInfoById(tutoringId)
            .orElseThrow(() -> new IllegalArgumentException("No Such Id"));
    return new GetTutoringDetailDto(
        tutoring.getTutoringName(),
        LocalDateTime.parse(tutoring.getStartedAt(), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
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
