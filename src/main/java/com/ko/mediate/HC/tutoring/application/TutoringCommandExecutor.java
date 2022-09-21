package com.ko.mediate.HC.tutoring.application;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.CommonResponseDto;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutoring.application.request.RequestProgressDto;
import com.ko.mediate.HC.tutoring.application.request.RequestTutoringDto;
import com.ko.mediate.HC.tutoring.application.request.TutoringResponseDto;
import com.ko.mediate.HC.tutoring.application.request.TutoringResponseType;
import com.ko.mediate.HC.tutoring.domain.Progress;
import com.ko.mediate.HC.tutoring.domain.Tutoring;
import com.ko.mediate.HC.tutoring.infra.JpaTutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutoringCommandExecutor {
    private final JpaTutoringRepository tutoringRepository;

    private Tutoring findByTutoringId(Long tutoringId) {
        return tutoringRepository
                .findById(tutoringId)
                .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @Transactional
    public CommonResponseDto responseTutoring(
            long tutoringId, UserInfo userInfo, TutoringResponseDto dto) {
        Tutoring tutoring = findByTutoringId(tutoringId);
        tutoring.isTutoringMember(userInfo.getAccountEmail());

        if (TutoringResponseType.REFUSE == dto.getResponseType()) {
            tutoring.cancelTutoring();
        } else if (TutoringResponseType.ACCEPT == dto.getResponseType()) {
            tutoring.acceptTutoring();
        }
        tutoringRepository.save(tutoring);
        return new CommonResponseDto("튜터링이 " + dto.getResponseType().getMessage() + " 되었습니다.");
    }

    @Transactional
    public void requestTutoring(RequestTutoringDto dto, UserInfo userInfo) {
        Tutoring tutoring =
                Tutoring.builder()
                        .tutoringName(dto.getTutoringName())
                        .tutorEmail(dto.getTutorEmail())
                        .tuteeEmail(dto.getTuteeEmail())
                        .build();
        tutoring.requestTutoring();
        tutoringRepository.save(tutoring);
    }

    @Transactional
    public boolean cancelTutoring(Long tutoringId, UserInfo userInfo) {
        Tutoring tutoring = findByTutoringId(tutoringId);
        tutoring.isTutoringMember(userInfo.getAccountEmail());
        tutoring.cancelTutoring();
        tutoringRepository.save(tutoring);
        return true;
    }

    @Transactional
    public void updateTutoring(long tutoringId, UserInfo userInfo, RequestTutoringDto dto) {
        Tutoring tutoring = findByTutoringId(tutoringId);
        tutoring.isTutoringMember(userInfo.getAccountEmail());
        tutoring.changeTutoringName(dto.getTutoringName());
    }

    @Transactional
    public void addProgressInTutoring(long tutoringId, RequestProgressDto dto, UserInfo userInfo) {
        Tutoring tutoring = findByTutoringId(tutoringId);
        tutoring.isTutoringMember(userInfo.getAccountEmail());
        tutoring.addProgress(new Progress(dto.getWeek(), dto.getContent(), dto.getIsCompleted()));
        tutoringRepository.save(tutoring);
    }

    @Transactional
    public void modifyProgressInTutoring(
            long tutoringId, long progressId, RequestProgressDto dto, UserInfo userInfo) {
        Tutoring tutoring = findByTutoringId(tutoringId);
        tutoring.isTutoringMember(userInfo.getAccountEmail());
        tutoring.modifyProgress(progressId, dto.getWeek(), dto.getContent(), dto.getIsCompleted());
    }

    @Transactional
    public void removeProgressInTutoring(long tutoringId, long progressId, UserInfo userInfo) {
        Tutoring tutoring = findByTutoringId(tutoringId);
        tutoring.isTutoringMember(userInfo.getAccountEmail());
        tutoring.removeProgress(progressId);
    }
}
