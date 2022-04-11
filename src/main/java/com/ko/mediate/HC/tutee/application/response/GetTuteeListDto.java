package com.ko.mediate.HC.tutee.application.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTuteeListDto {
  private List<GetTuteeDto> contents;
  private boolean hasNext;
}
