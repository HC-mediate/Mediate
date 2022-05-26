package com.ko.mediate.HC.aws.domain;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageStorage {
  String uploadFile(MultipartFile file, String key) throws IOException;
  void deleteFile(String key);
}
