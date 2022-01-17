package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.tutoring.application.TutoringCommandExecutor;
import com.ko.mediate.HC.tutoring.application.dto.request.RequestTutoringDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

  @PostMapping(value = "/tutoring")
  public ResponseEntity requestTutoring(
      @RequestHeader(name = "Authorization") String AuthValue, @RequestBody RequestTutoringDto dto) {
    commandExecutor.requestTutoring(AuthValue, dto);
    return ResponseEntity.ok("요청을 보냈습니다.");
  }
}
