package com.ko.mediate.HC.aws.application;

import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.aws.domain.ProfileImageStorage;
import com.ko.mediate.HC.aws.exception.MediateUnsupportImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static com.ko.mediate.HC.aws.ProfileFactory.createProfileRequest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfileService 예외 테스트")
public class ProfileImageServiceTest {

    @Mock
    ProfileImageStorage profileImageStorage;

    @Mock
    JpaAccountRepository accountRepository;

    ProfileImageService profileImageService;

    @BeforeEach
    void beforeEach() {
        profileImageService = new ProfileImageService(accountRepository, profileImageStorage);
    }

    @DisplayName("지원하지 않는 이미지 타입")
    @Test
    void unsupportTypeTest() {
        // given
        MockMultipartFile file =
                new MockMultipartFile("file", "file.mp4", MediaType.IMAGE_JPEG_VALUE, new byte[]{1});

        // when, then
        assertThatThrownBy(() -> profileImageService.uploadProfileImage(null, createProfileRequest(file)))
                .isInstanceOf(MediateUnsupportImageType.class);
    }
}
