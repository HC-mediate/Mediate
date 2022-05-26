package com.ko.mediate.HC.aws;

import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.ko.mediate.HC.aws.application.request.ProfileRequestDto;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.springframework.mock.web.MockMultipartFile;

public class ProfileFactory {
  public static ProfileRequestDto createProfileRequest(MockMultipartFile file) throws Exception {
    Constructor<ProfileRequestDto> constructor = ProfileRequestDto.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    ProfileRequestDto dto = constructor.newInstance();
    setField(dto, "file", file);
    return dto;
  }
}
