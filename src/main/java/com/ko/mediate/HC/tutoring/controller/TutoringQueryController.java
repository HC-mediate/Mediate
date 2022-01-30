package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.tutee.application.TuteeQueryProcessor;
import com.ko.mediate.HC.tutor.application.TutorQueryProcessor;
import com.ko.mediate.HC.tutoring.application.AuthService;
import com.ko.mediate.HC.tutoring.application.TutoringQueryProcessor;
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
  private final TutorQueryProcessor tutorQueryProcessor;
  private final TuteeQueryProcessor tuteeQueryProcessor;

  @ApiOperation(value = "튜터 정보 상세 조회")
  @GetMapping(value = "/tutors/{accountId}")
  public ResponseEntity<GetTutorDto> getTutorDetail(
      @PathVariable @ApiParam(value = "계정 ID") String accountId) {
    return ResponseEntity.ok(tutorQueryProcessor.getTutorDetail(accountId));
  }

  @ApiOperation(value = "튜티 정보 상세 조회")
  @GetMapping(value = "/tutees/{accountId}")
  public ResponseEntity<GetTuteeDto> getTuteeDetail(
      @PathVariable @ApiParam(value = "계정 ID") String accountId) {
    return ResponseEntity.ok(tuteeQueryProcessor.getTuteeDetail(accountId));
  }

  @ApiOperation(value = "튜터 정보 목록 조회")
  @GetMapping(value = "/tutors")
  public ResponseEntity<List<GetTutorDto>> getAllTutor() {
    return ResponseEntity.ok(tutorQueryProcessor.getAllTutor());
  }

  @ApiOperation(value = "튜티 정보 목록 조회")
  @GetMapping(value = "/tutees")
  public ResponseEntity<List<GetTuteeDto>> getAllTutee() {
    return ResponseEntity.ok(tuteeQueryProcessor.getAllTutee());
  }

  @ApiOperation(value = "튜터링 정보 상세 조회")
  @GetMapping(value = "/tutorings/{tutoringId}")
  public ResponseEntity<GetTutoringDetailDto> getTutoringDetailById(@PathVariable long tutoringId) {
    return ResponseEntity.ok(tutoringQueryProcessor.getTutoringDetailById(tutoringId));
  }
}
