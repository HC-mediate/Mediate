package com.ko.mediate.HC.aws.application;

import com.amazonaws.util.StringUtils;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.aws.application.request.ProfileImageRequestDto;
import com.ko.mediate.HC.aws.application.response.ProfileImageResponseDto;
import com.ko.mediate.HC.aws.domain.ProfileImageStorage;
import com.ko.mediate.HC.aws.exception.MediateUnsupportImageType;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileImageService {
  private final JpaAccountRepository accountRepository;
  private final ProfileImageStorage profileImageStorage;
  private final List<String> imageTypes = List.of("jpg", "jpeg", "png");
  private final String PREFIX_PROFILE = "profile_";

  @Value("${cloud.aws.cloud_front.domain_name}")
  private String CLOUD_FRONT;

  private Account findAccountByEmail(String email) {
    return accountRepository.findAccountByEmail(email).orElseThrow(MediateNotFoundException::new);
  }

  @Transactional
  public ProfileImageResponseDto uploadProfileImage(UserInfo userInfo, ProfileImageRequestDto dto)
      throws IOException {
    validateImageFile(dto.getFile());
    Account account = findAccountByEmail(userInfo.getAccountEmail());
    if (Objects.nonNull(account.getProfileImage())) {
      profileImageStorage.deleteFile(account.getProfileImage().getProfileKey());
    }
    String uploadFileName = renameFileName(dto.getFile().getOriginalFilename());
    String uploadKey = profileImageStorage.uploadFile(dto.getFile(), uploadFileName);
    account.changeProfileImage(uploadKey, CLOUD_FRONT + "/" + uploadKey);
    return new ProfileImageResponseDto(uploadKey, CLOUD_FRONT + "/" + uploadKey);
  }

  private void validateImageFile(MultipartFile file) {
    if (StringUtils.isNullOrEmpty(file.getOriginalFilename())) {
      throw new MediateNotFoundException("파일을 업로드하지 않았습니다.");
    }
    String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1);
    if (!imageTypes.contains(ext)) {
      throw new MediateUnsupportImageType();
    }
  }

  private String getFileExt(String name) {
    return name.substring(name.indexOf(".") + 1);
  }

  private String renameFileName(String name) {
    return PREFIX_PROFILE + UUID.randomUUID() + "." + getFileExt(name);
  }
}
