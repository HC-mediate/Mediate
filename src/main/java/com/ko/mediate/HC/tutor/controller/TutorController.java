package com.ko.mediate.HC.tutor.controller;

import com.ko.mediate.HC.auth.annotation.TokenAccount;
import com.ko.mediate.HC.auth.resolver.TokenAccountInfo;
import com.ko.mediate.HC.tutor.application.TutorCommandExecutor;
import com.ko.mediate.HC.tutor.application.TutorQueryProcessor;
import com.ko.mediate.HC.tutor.application.request.TutorSignupDto;
import com.ko.mediate.HC.tutor.application.response.GetTutorAccountDto;
import com.ko.mediate.HC.tutor.application.response.GetTutorDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"튜터 생성, 조회 api"})
public class TutorController {
  private final TutorQueryProcessor tutorQueryProcessor;
  private final TutorCommandExecutor tutorCommandExecutor;

  @PostMapping(value = "/tutors/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> Signup(@Valid @RequestBody TutorSignupDto dto) {
    tutorCommandExecutor.tutorJoin(dto);
    return ResponseEntity.ok("회원가입 완료");
  }

  @ApiOperation(value = "튜터 마이페이지 정보 조회")
  @GetMapping(value = "/tutors/mypage")
  public ResponseEntity<GetTutorAccountDto> getTutorAccount(
      @TokenAccount TokenAccountInfo token) {
    return ResponseEntity.ok(tutorQueryProcessor.getTutorAccount(token));
  }

  @ApiOperation(value = "튜터 정보 목록 조회")
  @GetMapping(value = "/tutors")
  public ResponseEntity<Page<GetTutorDto>> getAllTutor(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "5") int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return ResponseEntity.ok(tutorQueryProcessor.getAllTutor(pageRequest));
  }

  @ApiOperation(value = "튜터 정보 상세 조회")
  @GetMapping(value = "/tutors/{accountId}")
  public ResponseEntity<GetTutorDto> getTutorDetail(
      @PathVariable @ApiParam(value = "계정 ID") String accountId) {
    return ResponseEntity.ok(tutorQueryProcessor.getTutorDetail(accountId));
  }
}
