package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.auth.resolver.TokenAccountInfo;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDto;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutoringQueryProcessor {
  private final JpaTutoringRepository tutoringRepository;

  public GetTutoringDto getTutoringDetailById(long tutoringId, TokenAccountInfo token) {
    Tutoring tutoring =
        tutoringRepository
            .findTutoringDetailInfoById(tutoringId)
            .orElseThrow(() -> new MediateNotFoundException("찾는 ID가 없습니다."));
    return new GetTutoringDto(
        tutoring.getTutoringName(),
        tutoring.getStartedAt(),
        tutoring.getProgresses().stream()
            .filter(p -> p.isComplete())
            .collect(Collectors.toList())
            .size(),
        tutoring.getProgresses().size());
  }
}
