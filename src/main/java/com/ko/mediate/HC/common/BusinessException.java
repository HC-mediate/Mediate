package com.ko.mediate.HC.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

  protected final String code;
  protected final String message;
  protected final HttpStatus httpStatus;

  public BusinessException(final BusinessExceptionReason reason) {
    this.code = reason.getCode();
    this.message = reason.getMessage();
    this.httpStatus = reason.getHttpStatus();
  }

  public BusinessException(
      final BusinessExceptionReason reason, final HttpStatus overridingHttpStatus) {
    this.code = reason.getCode();
    this.message = reason.getMessage();
    this.httpStatus = overridingHttpStatus;
  }

  public BusinessException(final BusinessExceptionReason reason, final Object... parameters) {
    if (parameters != null) {
      this.message = String.format(reason.getMessage(), parameters);
    } else {
      this.message = reason.getMessage();
    }
    this.code = reason.getCode();
    this.httpStatus = reason.getHttpStatus();
  }

  @Override
  public String getLocalizedMessage() {
    return getMessage();
  }

  public String toString() {
    return String.format(
        "BusinessException(code=%s, message=%s, httpStatus=%s)",
        this.getCode(), this.getMessage(), this.getHttpStatus().value());
  }
}
