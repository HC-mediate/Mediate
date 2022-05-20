package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.tutoring.application.TutoringQueryProcessor;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDetailDto;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"튜터링 조회용 api"})
public class TutoringQueryController {
  // 목록 조회, 상세 조회
  private final TutoringQueryProcessor tutoringQueryProcessor;

  @ApiOperation(value = "튜터링 정보 요약 조회")
  @GetMapping(value = "/tutorings")
  public ResponseEntity<List<GetTutoringDto>> getAllTutoringByAccountId(
      @LoginUser UserInfo token) {
    return ResponseEntity.ok(tutoringQueryProcessor.getAllTutoringByAccountId(token));
  }

  @ApiOperation(value = "튜터링 진행도 조회")
  @GetMapping(value = "/tutorings/{tutoringId}/progress")
  public ResponseEntity<GetTutoringDetailDto> getTutoringDetailById(@PathVariable long tutoringId) {
    return ResponseEntity.ok(tutoringQueryProcessor.getTutoringDetailById(tutoringId));
  }
}
