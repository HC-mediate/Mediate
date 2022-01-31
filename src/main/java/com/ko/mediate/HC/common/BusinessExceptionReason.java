package com.ko.mediate.HC.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessExceptionReason {
  NO_SUCH_ID("No such id: %s", HttpStatus.BAD_REQUEST);

  private final String code = BusinessExceptionReason.class.getSimpleName();
  private final String message;
  private final HttpStatus httpStatus;
}
