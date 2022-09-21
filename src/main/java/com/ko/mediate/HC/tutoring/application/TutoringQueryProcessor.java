package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutoring.application.response.GetProgressDto;
import com.ko.mediate.HC.tutoring.application.response.GetTutoringDetailDto;
import com.ko.mediate.HC.tutoring.application.response.GetTutoringDto;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutoringQueryProcessor {
    private final JpaTutoringRepository tutoringRepository;

    public List<GetTutoringDto> getAllTutoringByAccountId(UserInfo userInfo) {
        return tutoringRepository.findAllTutoringByAccountEmail(userInfo.getAccountEmail()).stream()
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
                        .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        return new GetTutoringDetailDto(
                tutoring.getTutoringName(),
                tutoring.getStartedAt(),
                tutoring.getProgresses().stream()
                        .map(
                                p -> new GetProgressDto(p.getId(), p.getWeek(), p.getContent(), p.getIsCompleted()))
                        .collect(Collectors.toList()));
    }
}
