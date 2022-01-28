package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.tutoring.application.JoinService;
import com.ko.mediate.HC.tutoring.application.dto.request.TuteeSignupDto;
import com.ko.mediate.HC.tutoring.application.dto.request.TutorSignupDto;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"회원가입용 api"})
public class JoinController {
  private final JoinService joinService;

  @PostMapping(value = "/tutor/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> Signup(@Valid @RequestBody TutorSignupDto dto) {
    joinService.Join(dto);
    return ResponseEntity.ok("회원가입 완료");
  }

  @PostMapping(value = "/tutee/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> Signup(@Valid @RequestBody TuteeSignupDto dto) {
    joinService.Join(dto);
    return ResponseEntity.ok("회원가입 완료");
  }
}
