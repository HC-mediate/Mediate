package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.auth.resolver.TokenAccountInfo;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDetailDto;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutoringQueryProcessor {
  private final JpaTutoringRepository tutoringRepository;

  public GetTutoringDetailDto getTutoringDetailById(long tutoringId, TokenAccountInfo token) {
    Tutoring tutoring =
        tutoringRepository
            .findTutoringDetailInfoById(tutoringId)
            .orElseThrow(() -> new MediateNotFoundException("찾는 ID가 없습니다."));
    return new GetTutoringDetailDto(
        tutoring.getTutoringName(),
        tutoring.getStartedAt(),
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
    return null;
  }
}
