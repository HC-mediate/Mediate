package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.common.exception.MediateNotFoundException;
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

  public Tutoring findByTutoringId(long tutoringId) {
    return tutoringRepository
        .findById(tutoringId)
        .orElseThrow(() -> new MediateNotFoundException("찾는 ID가 없습니다."));
  }

  @Transactional
  public TutoringResponseType responseTutoring(long tutoringId, TutoringResponseDto dto) {
    Tutoring tutoring = findByTutoringId(tutoringId);
    if (dto.getResponseType() == TutoringResponseType.REFUSE) {
      tutoring.refuseTutoring();
    } else {
      tutoring.acceptTutoring();
    }
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
    return tutoring.cancelTutoring();
  }

  @Transactional
  public void updateTutoring(long tutoringId, RequestTutoringDto dto) {
    Tutoring tutoring = findByTutoringId(tutoringId);
    tutoring.changeTutoringName(dto.getTutoringName());
  }
}
