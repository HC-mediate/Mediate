package com.ko.mediate.HC.admin.controller;

import com.ko.mediate.HC.admin.service.AdminService;
import com.ko.mediate.HC.admin.service.dto.response.AdminAccountListDto;
import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
  private final AdminService adminService;

  @GetMapping("/accounts")
  public ResponseEntity<AdminAccountListDto> getAllAccounts(
      @LoginUser UserInfo userInfo,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {
    return ResponseEntity.ok(adminService.getAllAccounts(PageRequest.of(page, size)));
  }
}
