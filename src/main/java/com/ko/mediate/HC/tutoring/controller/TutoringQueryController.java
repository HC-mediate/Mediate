package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.auth.annotation.TokenAccount;
import com.ko.mediate.HC.auth.resolver.TokenAccountInfo;
import com.ko.mediate.HC.tutoring.application.TutoringQueryProcessor;
import com.ko.mediate.HC.tutoring.application.dto.response.GetTutoringDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

  @ApiOperation(value = "튜터링 정보 상세 조회")
  @GetMapping(value = "/tutorings/{tutoringId}")
  public ResponseEntity<GetTutoringDto> getTutoringDetailById(
      @TokenAccount TokenAccountInfo token,
      @PathVariable long tutoringId) {
    return ResponseEntity.ok(tutoringQueryProcessor.getTutoringDetailById(tutoringId, token));
  }
}
