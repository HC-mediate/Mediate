package com.ko.mediate.HC.tutor.application.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetTutorListDto {
  private List<GetTutorDto> contents;
  private boolean hasNext;
}
