package com.ko.mediate.HC.tutee.application.response;

import com.ko.mediate.HC.tutee.domain.Tutee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTuteeListDto {
    private List<GetTuteeDto> contents;
    private boolean hasNext;

    public static GetTuteeListDto fromEntites(Slice<Tutee> tutees) {
        return new GetTuteeListDto(
                tutees.stream().map(GetTuteeDto::fromEntity).collect(Collectors.toList()),
                tutees.hasNext());
    }
}
