package com.ko.mediate.HC.tutor.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.CommonResponseDto;
import com.ko.mediate.HC.common.domain.DistanceCondition;
import com.ko.mediate.HC.tutor.application.TutorCommandExecutor;
import com.ko.mediate.HC.tutor.application.TutorQueryProcessor;
import com.ko.mediate.HC.tutor.application.request.TutorSignupDto;
import com.ko.mediate.HC.tutor.application.response.GetTutorDto;
import com.ko.mediate.HC.tutor.application.response.GetTutorListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

  @PostMapping(value = "/tutors", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity Signup(
      @LoginUser UserInfo userInfo, @Valid @RequestBody TutorSignupDto dto) {
    tutorCommandExecutor.tutorJoin(userInfo, dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @ApiOperation(value = "튜터 정보 목록 조회")
  @GetMapping(value = "/tutors")
  public ResponseEntity<GetTutorListDto> getAllTutor(
      @LoginUser UserInfo userInfo,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "5") int size,
      @RequestParam(value = "radius", defaultValue = "5") int radius) {
    return ResponseEntity.ok(
        tutorQueryProcessor.getAllTutorByDistance(userInfo, PageRequest.of(page, size), radius));
  }

  @ApiOperation(value = "튜터 정보 상세 조회")
  @GetMapping(value = "/tutors/{tutorId}")
  public ResponseEntity<GetTutorDto> getTutorDetail(
      @PathVariable @ApiParam(value = "튜터 ID") Long tutorId) {
    return ResponseEntity.ok(tutorQueryProcessor.getTutorDetail(tutorId));
  }
}
