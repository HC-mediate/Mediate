package com.ko.mediate.HC.aws;

import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.common.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("프로필 이미지 api 테스트")
public class ProfileApiTest extends BaseApiTest {
    @Autowired
    MockMvc mvc;

    @Value("${cloud.aws.cloud_front.domain_name}")
    String cloud_front;

    @Test
    void 프로필_이미지_업로드_성공시_201을_반환한다() throws Exception {
        // given
        String fileName = "test.jpg", ext = "jpg";
        MockMultipartFile file =
                new MockMultipartFile("file", fileName, MediaType.IMAGE_JPEG_VALUE, new byte[]{1});

        // when
        ResultActions result =
                mvc.perform(
                        multipart("/api/profile-image")
                                .file(file)
                                .header(AUTHORIZATION, accessTokenMap.get(accountHasNoProfile.getId())));

        // then
        result
                .andExpect(jsonPath("$.key").value(containsString(ext)))
                .andExpect(status().isCreated())
                .andDo(print());

        Account account = accountRepository.findById(accountHasNoProfile.getId()).get();
        assertThat(account.getProfileImage().getProfileUrl()).contains(ext, cloud_front);
    }

    @Test
    void 프로필_이미지_변경시_변경된_이미지의_업로드url이_반환된다() throws Exception {
        // given
        String fileName = "test.png", ext = "png";
        MockMultipartFile file =
                new MockMultipartFile("file", fileName, MediaType.IMAGE_PNG_VALUE, new byte[]{1});

        // when
        ResultActions result =
                mvc.perform(
                        multipart("/api/profile-image")
                                .file(file)
                                .header(AUTHORIZATION, accessTokenMap.get(accountHasProfile.getId())));

        // then
        result
                .andExpect(jsonPath("$.key").value(containsString(ext)))
                .andExpect(jsonPath("$.url").value(containsString(ext)))
                .andDo(print());

        Account account = accountRepository.findById(accountHasProfile.getId()).get();
        assertThat(account.getProfileImage().getProfileUrl()).contains(ext, cloud_front);
    }
}
