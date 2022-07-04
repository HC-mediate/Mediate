package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class MediateFileExceedLimitException extends MediateException {
  @Override
  public HttpStatus status() {
    return HttpStatus.BAD_REQUEST;
  }

  public MediateFileExceedLimitException(ErrorCode errorCode) {
    super(errorCode);
  }
}
