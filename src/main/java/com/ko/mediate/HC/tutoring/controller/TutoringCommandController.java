package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.tutoring.application.TutoringCommandExecutor;
import com.ko.mediate.HC.tutoring.application.dto.request.RequestTutoringDto;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class TutoringCommandController {
  private final TutoringCommandExecutor commandExecutor;

  @PostMapping(value = "/tutoring", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity requestTutoring(
      @RequestHeader(name = "Authorization") String authValue,
      @RequestBody RequestTutoringDto dto) {
    commandExecutor.requestTutoring(authValue, dto);
    return ResponseEntity.ok("요청을 보냈습니다.");
  }

  @PostMapping(value = "/tutoring/{tutoringId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity responseTutoring(
      @RequestHeader(name = "Authorization") String authValue,
      @PathVariable long tutoringId,
      @RequestBody TutoringResponseDto dto) {
    return ResponseEntity.ok(
        commandExecutor.TutoringResponse(authValue, tutoringId, dto).getMessage());
  }

  @DeleteMapping(value = "/tutoring/{tutoringId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity cancelTutoring(
      @RequestHeader(name = "Authorization") String authValue, @PathVariable long tutoringId) {
    commandExecutor.cancelTutoring(authValue, tutoringId);
    return ResponseEntity.ok("튜터링이 취소되었습니다.");
  }

  @GetMapping(value = "/tutoring/{tutoringId}")
  public ResponseEntity getTutoringDetail(@PathVariable long tutoringId) {
    return ResponseEntity.ok(commandExecutor.getTutoringDetail(tutoringId));
  }
}
