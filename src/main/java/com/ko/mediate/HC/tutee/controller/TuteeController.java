package com.ko.mediate.HC.tutee.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.facade.query.DistanceQueryFacade;
import com.ko.mediate.HC.tutee.application.TuteeCommandExecutor;
import com.ko.mediate.HC.tutee.application.TuteeQueryProcessor;
import com.ko.mediate.HC.tutee.application.request.TuteeSignupDto;
import com.ko.mediate.HC.tutee.application.response.GetTuteeDto;
import com.ko.mediate.HC.tutee.application.response.GetTuteeListDto;
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
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"튜티 생성, 조회 api"})
public class TuteeController {
  private final TuteeQueryProcessor tuteeQueryProcessor;
  private final TuteeCommandExecutor tuteeCommandExecutor;
  private final DistanceQueryFacade distanceQueryFacade;

  @PostMapping(value = "/tutees", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity Signup(
      @ApiIgnore @LoginUser UserInfo userInfo, @Valid @RequestBody TuteeSignupDto dto) {
    tuteeCommandExecutor.tuteeJoin(userInfo, dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @ApiOperation(value = "튜티 정보 목록 조회")
  @GetMapping(value = "/tutees")
  public ResponseEntity<GetTuteeListDto> getAllTutee(
      @ApiIgnore @LoginUser UserInfo userInfo,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "5") int size,
      @RequestParam(value = "radius", defaultValue = "5") int radius) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return ResponseEntity.ok(
        distanceQueryFacade.getAllTuteeByDistance(userInfo, pageRequest, radius));
  }

  @ApiOperation(value = "튜티 정보 상세 조회")
  @GetMapping(value = "/tutees/{tuteeId}")
  public ResponseEntity<GetTuteeDto> getTuteeDetail(
      @PathVariable @ApiParam(value = "튜티 ID") Long accountId) {
    return ResponseEntity.ok(tuteeQueryProcessor.getTuteeDetail(accountId));
  }
}
