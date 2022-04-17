package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.auth.resolver.TokenAccountInfo;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutoring.application.dto.response.GetProgressDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDetailDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDto;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutoringQueryProcessor {
  private final JpaTutoringRepository tutoringRepository;

  public List<GetTutoringDto> getAllTutoringByAccountId(TokenAccountInfo token) {
    return tutoringRepository.findAllTutoringByAccountId(token.getAccountId()).stream()
        .map(
            t ->
                new GetTutoringDto(
                    t.getId(),
                    t.getTutoringName(),
                    t.getStartedAt(),
                    t.getDoneWeek(),
                    t.getTotalWeek()))
        .collect(Collectors.toList());
  }

  public GetTutoringDetailDto getTutoringDetailById(long tutoringId) {
    Tutoring tutoring =
        tutoringRepository
            .findByTutoringIdWithDetail(tutoringId)
            .orElseThrow(() -> new MediateNotFoundException("ID가 없습니다."));

    return new GetTutoringDetailDto(
        tutoring.getTutoringName(),
        tutoring.getStartedAt(),
        tutoring.getProgresses().stream()
            .map(
                p -> new GetProgressDto(p.getId(), p.getWeek(), p.getContent(), p.getIsCompleted()))
            .collect(Collectors.toList()));
  }
}
