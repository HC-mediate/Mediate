package com.ko.mediate.HC.aws;

import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.ko.mediate.HC.aws.application.request.ProfileImageRequestDto;
import java.lang.reflect.Constructor;
import org.springframework.mock.web.MockMultipartFile;

public class ProfileFactory {
  public static ProfileImageRequestDto createProfileRequest(MockMultipartFile file) throws Exception {
    Constructor<ProfileImageRequestDto> constructor = ProfileImageRequestDto.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    ProfileImageRequestDto dto = constructor.newInstance();
    setField(dto, "file", file);
    return dto;
  }
}
