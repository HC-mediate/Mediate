package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutoring.application.dto.response.GetProgressDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDetailDto;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProgressQueryProcessor {
  private final JpaTutoringRepository tutoringRepository;

  public GetTutoringDetailDto getAllProgressInTutoring(long tutoringId) {
    Tutoring tutoring =
        tutoringRepository
            .findTutoringDetailInfoById(tutoringId)
            .orElseThrow(() -> new MediateNotFoundException("ID가 없습니다."));

    return new GetTutoringDetailDto(
        tutoring.getTutoringName(),
        tutoring.getStartedAt(),
        tutoring.getProgresses().stream()
            .map(p -> new GetProgressDto(p.getWeek(), p.getContent(), p.getIsCompleted()))
            .collect(Collectors.toList()));
  }
}
