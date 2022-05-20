package com.ko.mediate.HC.tutee.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.CommonResponseDto;
import com.ko.mediate.HC.common.domain.DistanceCondition;
import com.ko.mediate.HC.tutee.application.TuteeCommandExecutor;
import com.ko.mediate.HC.tutee.application.TuteeQueryProcessor;
import com.ko.mediate.HC.tutee.application.request.TuteeSignupDto;
import com.ko.mediate.HC.tutee.application.response.GetTuteeAccountDto;
import com.ko.mediate.HC.tutee.application.response.GetTuteeDto;
import com.ko.mediate.HC.tutee.application.response.GetTuteeListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Api(tags = {"튜티 생성, 조회 api"})
public class TuteeController {
  private final TuteeQueryProcessor tuteeQueryProcessor;
  private final TuteeCommandExecutor tuteeCommandExecutor;

  @PostMapping(value = "/tutees/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommonResponseDto> Signup(@Valid @RequestBody TuteeSignupDto dto) {
    tuteeCommandExecutor.tuteeJoin(dto);
    return ResponseEntity.ok(new CommonResponseDto("회원가입 완료"));
  }

  @ApiOperation(value = "튜티 마이페이지 정보 조회")
  @GetMapping(value = "/tutees/mypage")
  public ResponseEntity<GetTuteeAccountDto> getTuteeAccount(@LoginUser UserInfo token) {
    return ResponseEntity.ok(tuteeQueryProcessor.getTuteeAccount(token));
  }

  @ApiOperation(value = "튜티 정보 목록 조회")
  @GetMapping(value = "/tutees")
  public ResponseEntity<GetTuteeListDto> getAllTutee(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "5") int size,
      @RequestParam(value = "longitude", required = true) double longitude,
      @RequestParam(value = "latitude", required = true) double latitude,
      @RequestParam(value = "radius", defaultValue = "5") int radius) {
    PageRequest pageRequest = PageRequest.of(page, size);
    DistanceCondition condition = new DistanceCondition(longitude, latitude, radius);
    return ResponseEntity.ok(tuteeQueryProcessor.getAllTuteeByDistance(pageRequest, condition));
  }

  @ApiOperation(value = "튜티 정보 상세 조회")
  @GetMapping(value = "/tutees/{accountId}")
  public ResponseEntity<GetTuteeDto> getTuteeDetail(
      @PathVariable @ApiParam(value = "계정 ID") String accountId) {
    return ResponseEntity.ok(tuteeQueryProcessor.getTuteeDetail(accountId));
  }
}
