package com.ko.mediate.HC.controller;

import com.ko.mediate.HC.domain.Account;
import com.ko.mediate.HC.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AccountController {
  private final AccountRepository accountRepository;
  @GetMapping(value = "/{id}")
  public ResponseEntity getAccountDetailById(@PathVariable Long id){
    Account findAccount = accountRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("계정이 없음"));
    AccountDto dto = new AccountDto(findAccount.getEmail(), findAccount.getName());
    return ResponseEntity.ok(dto);
  }
}
