package com.ko.mediate.HC.tutor.application;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutor.application.dto.response.GetTutorDto;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutor.infra.JpaTutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutorQueryProcessor {
    private final JpaTutorRepository tutorRepository;

    public GetTutorDto getTutorDetail(Long tutorId) {
        return GetTutorDto.fromEntity(
                tutorRepository
                        .findByIdFetchAccount(tutorId)
                        .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND)));
    }

    public Tutor getTutorByAccountEmail(String email) {
        return tutorRepository.findTutorByAccountEmail(email).orElseGet(null);
    }

    public List<Tutor> getAllTutorsByAccountEmail(List<String> emails) {
        return tutorRepository.findAllByAccountEmails(emails);
    }
}
