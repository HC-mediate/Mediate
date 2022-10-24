package com.ko.mediate.HC.tutor.application.dto.response;

import com.ko.mediate.HC.tutor.domain.Tutor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetTutorListDto {
    private List<GetTutorDto> contents;
    private boolean hasNext;

    public static GetTutorListDto fromEntities(Slice<Tutor> tutors) {
        return new GetTutorListDto(
                tutors.stream().map(GetTutorDto::fromEntity).collect(Collectors.toList()),
                tutors.hasNext());
    }
}
