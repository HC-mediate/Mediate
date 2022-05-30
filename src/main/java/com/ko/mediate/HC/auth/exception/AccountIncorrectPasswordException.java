package com.ko.mediate.HC.auth.exception;

import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;

public class AccountIncorrectPasswordException extends MediateException {
  @Override
  public HttpStatus status() {
    return HttpStatus.BAD_REQUEST;
  }

  public AccountIncorrectPasswordException() {super("비밀번호가 일치하지 않습니다.");}

  public AccountIncorrectPasswordException(String message) {
    super(message);
  }
}
