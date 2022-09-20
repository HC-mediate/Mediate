package com.ko.mediate.HC.aws.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileImageStorage {
    String uploadFile(MultipartFile file, String key) throws IOException;

    void deleteFile(String key);
}
