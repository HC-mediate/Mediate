package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;

public class AccountIncorrectPasswordException extends MediateException {
  @Override
  public HttpStatus status() {
    return HttpStatus.BAD_REQUEST;
  }

  public AccountIncorrectPasswordException(ErrorCode errorCode) {
    super(errorCode);
  }

}
