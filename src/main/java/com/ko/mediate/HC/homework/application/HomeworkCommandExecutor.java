package com.ko.mediate.HC.homework.application;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.homework.application.dto.request.CreateHomeworkDto;
import com.ko.mediate.HC.homework.application.dto.request.UpdateHomeworkDto;
import com.ko.mediate.HC.homework.domain.Homework;
import com.ko.mediate.HC.homework.domain.HomeworkContent;
import com.ko.mediate.HC.homework.infra.JpaHomeworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeworkCommandExecutor {
    private final JpaHomeworkRepository homeworkRepository;

    @Transactional
    public void createHomework(CreateHomeworkDto dto) {
        Homework homework = new Homework(dto.getTutorId(), dto.getTuteeId(), dto.getTitle(), false);
        dto.getContents().stream().forEach(c -> homework.giveHomework(c.getContent(), c.isCompleted()));
        homeworkRepository.save(homework);
    }

    @Transactional
    public void modifyHomework(Long homeworkId, UpdateHomeworkDto dto) {
        Homework homework =
                homeworkRepository
                        .findById(homeworkId)
                        .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        homework.changeHomework(
                dto.getTitle(),
                dto.getContents().stream()
                        .map(c -> new HomeworkContent(c.getContent(), c.isCompleted()))
                        .collect(Collectors.toList()));
        homeworkRepository.save(homework);
    }

    @Transactional
    public void deleteHomework(long homeworkId) {
        homeworkRepository.deleteById(homeworkId);
    }
}
