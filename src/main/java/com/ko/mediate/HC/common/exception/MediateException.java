package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class MediateException extends RuntimeException {
  private final ErrorCode errorCode;

  public MediateException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public MediateException(final String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public abstract HttpStatus status();
}
