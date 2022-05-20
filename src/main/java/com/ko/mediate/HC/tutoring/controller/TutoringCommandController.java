package com.ko.mediate.HC.tutoring.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.CommonResponseDto;
import com.ko.mediate.HC.tutoring.application.TutoringCommandExecutor;
import com.ko.mediate.HC.tutoring.application.dto.request.RequestProgressDto;
import com.ko.mediate.HC.tutoring.application.dto.request.RequestTutoringDto;
import com.ko.mediate.HC.tutoring.application.dto.request.TutoringResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"튜터링 생성(제안), 업데이트, 삭제용 api"})
public class TutoringCommandController {
  private final TutoringCommandExecutor commandExecutor;

  @PostMapping(value = "/tutorings", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "튜터링을 생성(제안)하는 api")
  public ResponseEntity<CommonResponseDto> requestTutoring(
      @LoginUser UserInfo token, @Valid @RequestBody RequestTutoringDto dto) {
    commandExecutor.requestTutoring(dto, token);
    return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDto("요청을 보냈습니다."));
  }

  @ApiOperation(value = "튜터링 제안에 대한 응답을 보내는 api")
  @PostMapping(value = "/tutorings/{tutoringId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommonResponseDto> responseTutoring(
      @LoginUser UserInfo token,
      @PathVariable long tutoringId,
      @Valid @RequestBody TutoringResponseDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(commandExecutor.responseTutoring(tutoringId, token, dto));
  }

  @ApiOperation(value = "튜터링을 취소하는 api")
  @DeleteMapping(value = "/tutorings/{tutoringId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommonResponseDto> cancelTutoring(
      @LoginUser UserInfo token, @PathVariable long tutoringId) {
    commandExecutor.cancelTutoring(tutoringId, token);
    return ResponseEntity.ok(new CommonResponseDto("튜터링이 취소되었습니다."));
  }

  @ApiOperation(value = "튜터링 정보를 업데이트하는 api")
  @PutMapping(value = "/tutorings/{tutoringId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommonResponseDto> updateTutoring(
      @LoginUser UserInfo token,
      @PathVariable long tutoringId,
      @Valid @RequestBody RequestTutoringDto dto) {
    commandExecutor.updateTutoring(tutoringId, token, dto);
    return ResponseEntity.ok(new CommonResponseDto("튜터링 정보가 업데이트 되었습니다."));
  }

  @ApiOperation(value = "튜터링 진행도를 추가하는 api")
  @PostMapping(
      value = "/tutorings/{tutoringId}/progress",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommonResponseDto> createProgressInTutoring(
      @PathVariable long tutoringId, @Valid @RequestBody RequestProgressDto dto) {
    commandExecutor.addProgressInTutoring(tutoringId, dto);
    return ResponseEntity.ok(new CommonResponseDto("튜터링 진행도를 추가했습니다."));
  }

  @ApiOperation(value = "튜터링 진행도를 수정하는 api")
  @PutMapping(
      value = "/tutorings/{tutoringId}/progress/{progressId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommonResponseDto> modifyProgressInTutoring(
      @PathVariable long tutoringId,
      @PathVariable long progressId,
      @Valid @RequestBody RequestProgressDto dto) {
    commandExecutor.modifyProgressInTutoring(tutoringId, progressId, dto);
    return ResponseEntity.ok(new CommonResponseDto("튜터링 진행도를 수정했습니다."));
  }

  @ApiOperation(value = "튜터링 진행도를 삭제하는 api")
  @DeleteMapping(
      value = "/tutorings/{tutoringId}/progress/{progressId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommonResponseDto> removeProgressInTutoring(
      @PathVariable long tutoringId, @PathVariable long progressId) {
    commandExecutor.removeProgressInTutoring(tutoringId, progressId);
    return ResponseEntity.ok(new CommonResponseDto("튜터링 진행도를 삭제했습니다."));
  }
}
