package com.ko.mediate.HC.aws.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.s3.AmazonS3Client;
import com.ko.mediate.HC.common.BaseApiTest;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class ProfileImageStorageTest extends BaseApiTest {
  @Autowired ProfileImageStorage profileImageStorage;

  @Autowired AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  String bucket;

  @DisplayName("이미지 정상 업로드")
  @Test
  void imageUploadTest() throws IOException {
    // given
    String uploadFileName = UUID.randomUUID().toString() + ".jpeg";
    MockMultipartFile file =
        new MockMultipartFile("file", "file.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[] {1});

    // when
    String uploadKey = profileImageStorage.uploadFile(file, uploadFileName);

    // then
    assertThat(uploadKey).isEqualTo("profile/" + uploadFileName);
  }
}
