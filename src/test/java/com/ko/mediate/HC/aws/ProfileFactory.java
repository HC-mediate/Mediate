package com.ko.mediate.HC.aws;

import com.ko.mediate.HC.aws.application.dto.request.ProfileImageRequestDto;
import org.springframework.mock.web.MockMultipartFile;

import java.lang.reflect.Constructor;

import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ProfileFactory {
    public static ProfileImageRequestDto createProfileRequest(MockMultipartFile file) throws Exception {
        Constructor<ProfileImageRequestDto> constructor = ProfileImageRequestDto.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        ProfileImageRequestDto dto = constructor.newInstance();
        setField(dto, "file", file);
        return dto;
    }
}
