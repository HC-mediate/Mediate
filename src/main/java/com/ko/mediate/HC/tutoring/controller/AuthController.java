package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.tutoring.application.AuthService;
import com.ko.mediate.HC.tutoring.application.dto.request.TuteeSignupDto;
import com.ko.mediate.HC.tutoring.application.dto.request.TutorSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/tutor/signup")
  public ResponseEntity<String> Signup(@RequestBody TutorSignupDto dto) {
    authService.tutorJoin(dto);
    return ResponseEntity.ok("회원가입 완료");
  }

  @PostMapping("/tutee/signup")
  public ResponseEntity<String> Signup(@RequestBody TuteeSignupDto dto) {
    authService.tuteeJoin(dto);
    return ResponseEntity.ok("회원가입 완료");
  }
}
