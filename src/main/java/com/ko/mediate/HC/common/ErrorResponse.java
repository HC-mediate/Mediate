package com.ko.mediate.HC.common;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "error")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  private List<String> message = new ArrayList<>();

  public ErrorResponse(BindingResult bindingResult) {
    for (FieldError e : bindingResult.getFieldErrors()) {
      this.message.add(e.getDefaultMessage());
    }
  }

  public ErrorResponse(String errorMessage) {
    this.message.add(errorMessage);
  }
}
