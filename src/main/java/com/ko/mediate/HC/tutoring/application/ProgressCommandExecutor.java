package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutoring.application.dto.request.RequestProgressDto;
import com.ko.mediate.HC.tutoring.domain.Progress;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProgressCommandExecutor {
  private final JpaTutoringRepository tutoringRepository;

  private Tutoring findByTutoringId(long tutoringId) {
    return tutoringRepository
        .findById(tutoringId)
        .orElseThrow(() -> new MediateNotFoundException("ID가 없습니다."));
  }

  @Transactional
  public void addProgressInTutoring(long tutoringId, RequestProgressDto dto) {
    Tutoring tutoring = findByTutoringId(tutoringId);
    tutoring.addProgress(new Progress(dto.getWeek(), dto.getContent(), dto.getIsCompleted()));
    tutoringRepository.save(tutoring);
  }

  @Transactional
  public void modifyProgressInTutoring(long tutoringId, RequestProgressDto dto) {
    Tutoring tutoring = findByTutoringId(tutoringId);
    tutoring.modifyProgress(
        tutoringId, new Progress(dto.getWeek(), dto.getContent(), dto.getIsCompleted()));
  }

  @Transactional
  public void removeProgressInTutoring(long tutoringId) {
    Tutoring tutoring = findByTutoringId(tutoringId);
    tutoring.removeProgress(tutoringId);
  }
}
