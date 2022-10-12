package com.ko.mediate.HC.homework.controller;

import com.ko.mediate.HC.homework.application.HomeworkCommandExecutor;
import com.ko.mediate.HC.homework.application.HomeworkQueryProcessor;
import com.ko.mediate.HC.homework.application.request.CreateHomeworkDto;
import com.ko.mediate.HC.homework.application.request.UpdateHomeworkDto;
import com.ko.mediate.HC.homework.application.response.GetHomeworkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/homework")
public class HomeworkController {

    private final HomeworkCommandExecutor homeworkCommandExecutor;
    private final HomeworkQueryProcessor homeworkQueryProcessor;

    @GetMapping(value = "/tutor/{tutorId}")
    public ResponseEntity<List<GetHomeworkDto>> getAllHomeworkByTutorId(
            @PathVariable String tutorId) {
        return ResponseEntity.ok(homeworkQueryProcessor.getAllHomeworkByTutorId(tutorId));
    }

    @GetMapping(value = "/tutee/{tuteeId}")
    public ResponseEntity<List<GetHomeworkDto>> getAllHomeworkByTuteeId(
            @PathVariable String tuteeId) {
        return ResponseEntity.ok(homeworkQueryProcessor.getAllHomeworkByTuteeId(tuteeId));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHomework(@RequestBody CreateHomeworkDto dto) {
        homeworkCommandExecutor.createHomework(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "{homeworkId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> modifyHomework(@PathVariable Long homeworkId,
            @RequestBody UpdateHomeworkDto dto) {
        homeworkCommandExecutor.modifyHomework(homeworkId, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
