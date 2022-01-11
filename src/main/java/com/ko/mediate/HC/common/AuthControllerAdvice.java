package com.ko.mediate.HC.common;

import com.ko.mediate.HC.account.controller.AuthController;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = AuthController.class)
public class AuthControllerAdvice {
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Response> AuthenticationExceptionAdvice(AuthenticationException e) {
    final ErrorResponse response = new ErrorResponse(e.getMessage());
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }
}
