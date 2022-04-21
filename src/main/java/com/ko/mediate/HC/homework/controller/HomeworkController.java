package com.ko.mediate.HC.homework.controller;

import com.ko.mediate.HC.common.CommonResponseDto;
import com.ko.mediate.HC.homework.application.HomeworkCommandExecutor;
import com.ko.mediate.HC.homework.application.HomeworkQueryProcessor;
import com.ko.mediate.HC.homework.application.request.CreateHomeworkDto;
import com.ko.mediate.HC.homework.application.request.UpdateHomeworkDto;
import com.ko.mediate.HC.homework.application.response.GetHomeworkDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<CommonResponseDto> createHomework(@RequestBody CreateHomeworkDto dto) {
    homeworkCommandExecutor.createHomework(dto);
    return ResponseEntity.ok(new CommonResponseDto("숙제를 추가하였습니다."));
  }

  @PutMapping(value = "{homeworkId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommonResponseDto> modifyHomework(@PathVariable Long homeworkId, @RequestBody UpdateHomeworkDto dto) {
    homeworkCommandExecutor.modifyHomework(homeworkId, dto);
    return ResponseEntity.ok(new CommonResponseDto("숙제를 수정하였습니다."));
  }
}
