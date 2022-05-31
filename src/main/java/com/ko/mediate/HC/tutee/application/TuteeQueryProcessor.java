package com.ko.mediate.HC.tutee.application;

import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutee.application.response.GetTuteeDto;
import com.ko.mediate.HC.tutee.infra.JpaTuteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TuteeQueryProcessor {
  private final JpaTuteeRepository tuteeRepository;

  public GetTuteeDto getTuteeDetail(Long tuteeId) {
    return GetTuteeDto.fromEntity(
        tuteeRepository.findById(tuteeId).orElseThrow(MediateNotFoundException::new));
  }

  public Tutee getTuteeByAccountEmail(String email) {
    return tuteeRepository.findByAccountEmail(email).orElseGet(null);
  }
}
