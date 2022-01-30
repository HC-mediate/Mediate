package com.ko.mediate.HC.tutee.controller;

import com.ko.mediate.HC.tutee.application.TuteeCommandExecutor;
import com.ko.mediate.HC.tutee.application.TuteeQueryProcessor;
import com.ko.mediate.HC.tutee.application.request.TuteeSignupDto;
import com.ko.mediate.HC.tutee.application.response.GetTuteeAccountDto;
import com.ko.mediate.HC.tutee.application.response.GetTuteeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(tags = {"튜티 생성, 조회 api"})
public class TuteeController {
  private final TuteeQueryProcessor tuteeQueryProcessor;
  private final TuteeCommandExecutor tuteeCommandExecutor;

  @PostMapping(value = "/tutees/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> Signup(@Valid @RequestBody TuteeSignupDto dto) {
    tuteeCommandExecutor.tuteeJoin(dto);
    return ResponseEntity.ok("회원가입 완료");
  }

  @ApiOperation(value = "튜티 마이페이지 정보 조회")
  @GetMapping(value = "/tutees/mypage/{accountId}")
  @PreAuthorize("@tokenProvider.isUserToken(#authValue, #accountId)")
  public ResponseEntity<GetTuteeAccountDto> getTuteeAccount(
      @RequestHeader("Authorization") String authValue, @PathVariable String accountId) {
    return ResponseEntity.ok(tuteeQueryProcessor.getTuteeAccount(accountId));
  }

  @ApiOperation(value = "튜티 정보 목록 조회")
  @GetMapping(value = "/tutees")
  public ResponseEntity<List<GetTuteeDto>> getAllTutee() {
    return ResponseEntity.ok(tuteeQueryProcessor.getAllTutee());
  }

  @ApiOperation(value = "튜티 정보 상세 조회")
  @GetMapping(value = "/tutees/{accountId}")
  public ResponseEntity<GetTuteeDto> getTuteeDetail(
      @PathVariable @ApiParam(value = "계정 ID") String accountId) {
    return ResponseEntity.ok(tuteeQueryProcessor.getTuteeDetail(accountId));
  }
}
