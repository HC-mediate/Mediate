package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.tutoring.application.AuthService;
import com.ko.mediate.HC.tutoring.application.TutoringQueryProcessor;
import com.ko.mediate.HC.tutoring.application.dto.response.GetAccountDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTuteeAccountDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTuteeDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutorAccountDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutorDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDetailDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Api(tags = {"튜터링 조회용 api"})
public class TutoringQueryController {
  // 목록 조회, 상세 조회
  private final TutoringQueryProcessor tutoringQueryProcessor;
  private final AuthService authService;

  @ApiOperation(value = "튜터 정보 상세 조회")
  @GetMapping(value = "/tutors/{accountId}")
  public ResponseEntity<GetTutorDto> getTutorDetail(
      @PathVariable @ApiParam(value = "계정 ID") String accountId) {
    return ResponseEntity.ok(tutoringQueryProcessor.getTutorDetail(accountId));
  }

  @ApiOperation(value = "튜티 정보 상세 조회")
  @GetMapping(value = "/tutees/{accountId}")
  public ResponseEntity<GetTuteeDto> getTuteeDetail(
      @PathVariable @ApiParam(value = "계정 ID") String accountId) {
    return ResponseEntity.ok(tutoringQueryProcessor.getTuteeDetail(accountId));
  }

  @ApiOperation(value = "튜터 정보 목록 조회")
  @GetMapping(value = "/tutors")
  public ResponseEntity<List<GetTutorDto>> getAllTutor() {
    return ResponseEntity.ok(tutoringQueryProcessor.getAllTutor());
  }

  @ApiOperation(value = "튜티 정보 목록 조회")
  @GetMapping(value = "/tutees")
  public ResponseEntity<List<GetTuteeDto>> getAllTutee() {
    return ResponseEntity.ok(tutoringQueryProcessor.getAllTutee());
  }

  @ApiOperation(value = "튜터링 정보 상세 조회")
  @GetMapping(value = "/tutorings/{tutoringId}")
  public ResponseEntity<GetTutoringDetailDto> getTutoringDetailById(@PathVariable long tutoringId) {
    return ResponseEntity.ok(tutoringQueryProcessor.getTutoringDetailById(tutoringId));
  }

  @ApiOperation(value = "튜터 마이페이지 정보 조회")
  @GetMapping(value = "/tutors/mypage/{accountId}")
  @PreAuthorize("@tokenProvider.isUserToken(#authValue, #accountId)")
  public ResponseEntity<GetTutorAccountDto> getTutorAccount(
      @RequestHeader("Authorization") String authValue, @PathVariable String accountId) {
    return ResponseEntity.ok(tutoringQueryProcessor.getTutorAccount(accountId));
  }

  @ApiOperation(value = "튜티 마이페이지 정보 조회")
  @GetMapping(value = "/tutees/mypage/{accountId}")
  @PreAuthorize("@tokenProvider.isUserToken(#authValue, #accountId)")
  public ResponseEntity<GetTuteeAccountDto> getTuteeAccount(
      @RequestHeader("Authorization") String authValue, @PathVariable String accountId) {
    return ResponseEntity.ok(tutoringQueryProcessor.getTuteeAccount(accountId));
  }
}
