package com.ko.mediate.HC.homework.application;

import com.ko.mediate.HC.homework.application.response.GetHomeworkDto;
import com.ko.mediate.HC.homework.infra.JpaHomeworkRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeworkQueryProcessor {
  private final JpaHomeworkRepository homeworkRepository;

  public List<GetHomeworkDto> getAllHomeworkByTutorId(String tutorId) {
    return homeworkRepository.findAllByTutorId(tutorId).stream()
        .map(h -> new GetHomeworkDto(h.getId(), h.getTitle(), h.getIsFinished(), h.getContents()))
        .collect(Collectors.toList());
  }

  public List<GetHomeworkDto> getAllHomeworkByTuteeId(String tuteeId) {
    return homeworkRepository.findAllByTuteeId(tuteeId).stream()
        .map(h -> new GetHomeworkDto(h.getId(), h.getTitle(), h.getIsFinished(), h.getContents()))
        .collect(Collectors.toList());
  }
}
