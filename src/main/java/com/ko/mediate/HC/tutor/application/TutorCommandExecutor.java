package com.ko.mediate.HC.tutor.application;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.domain.GeometryConverter;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutor.application.request.TutorSignupDto;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutor.infra.JpaTutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutorCommandExecutor {
    private final JpaTutorRepository tutorRepository;
    private final JpaAccountRepository accountRepository;
    private final GeometryConverter geometryConverter;

    private Account findAccountByEmail(UserInfo userInfo) {
        return accountRepository
                .findAccountByEmail(userInfo.getAccountEmail())
                .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @Transactional
    public void tutorJoin(UserInfo userInfo, TutorSignupDto dto) {
        Account account = findAccountByEmail(userInfo);
        account.joinTutor(geometryConverter.convertCoordinateToPoint(dto.getLocation()));

        Tutor tutor =
                Tutor.builder()
                        .account(account)
                        .school(dto.getSchool())
                        .major(dto.getMajor())
                        .grade(dto.getGrade())
                        .address(dto.getAddress())
                        .curriculums(dto.getCurriculums())
                        .build();
        tutorRepository.save(tutor);
    }
}
