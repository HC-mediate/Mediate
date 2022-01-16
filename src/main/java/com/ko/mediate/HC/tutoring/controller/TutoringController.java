package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.tutoring.application.TutoringService;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTuteeDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutorDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class TutoringController {
  // 목록 조회, 상세 조회
  private final TutoringService tutoringService;

  @GetMapping(value = "/tutor/{accountId}")
  public ResponseEntity<GetTutorDto> getTutorDetail(@PathVariable String accountId) {
    return ResponseEntity.ok(tutoringService.getTutorDetail(accountId));
  }

  @GetMapping(value = "/tutee/{accountId}")
  public ResponseEntity<GetTuteeDto> getTuteeDetail(@PathVariable String accountId) {
    return ResponseEntity.ok(tutoringService.getTuteeDetail(accountId));
  }

  @GetMapping(value = "/tutor")
  public ResponseEntity<List> getAllTutor() {
    return ResponseEntity.ok(tutoringService.getAllTutor());
  }

  @GetMapping(value = "/tutee")
  public ResponseEntity<List> getAllTutee() {
    return ResponseEntity.ok(tutoringService.getAllTutee());
  }
}
