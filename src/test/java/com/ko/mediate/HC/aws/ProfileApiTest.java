package com.ko.mediate.HC.aws;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.ko.mediate.HC.HcApplicationTests;
import com.ko.mediate.HC.auth.domain.Account;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public class ProfileApiTest extends HcApplicationTests {
  @Autowired MockMvc mvc;

  @Value("${cloud.aws.s3.bucket}")
  String bucket;

  @DisplayName("프로필 사진 업로드 테스트")
  @Test
  void profileImageUploadTest() throws Exception {
    // given
    String fileName = "test.jpg", ext = "jpg";
    MockMultipartFile file =
        new MockMultipartFile("file", fileName, MediaType.IMAGE_JPEG_VALUE, new byte[] {1});

    // when
    ResultActions result =
        mvc.perform(
            multipart("/api/profile-image")
                .file(file)
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken));

    // then
    result
        .andExpect(jsonPath("$.key").value(containsString(ext)))
        .andExpect(status().isCreated())
        .andDo(print());

    Account account = accountRepository.findById(saveId).get();
    assertThat(account.getProfileUrl()).contains(ext, bucket);
  }
}
