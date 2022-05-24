package com.ko.mediate.HC.auth.exception;

import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;

public class AccountNotFountException extends MediateException {

  @Override
  public HttpStatus status() {
    return HttpStatus.BAD_REQUEST;
  }

  public AccountNotFountException() {}

  public AccountNotFountException(String message) {
    super(message);
  }
}
