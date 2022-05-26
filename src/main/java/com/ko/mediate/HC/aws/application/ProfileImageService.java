package com.ko.mediate.HC.aws.application;

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
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileImageService {
  private final JpaAccountRepository accountRepository;
  private final ProfileImageStorage profileImageStorage;
  private final List<String> imageTypes = List.of("jpg", "jpeg", "png");
  private final String PREFIX_PROFILE = "profile_";

  private Account findAccountById(Long accountId) {
    return accountRepository.findById(accountId).orElseThrow(MediateNotFoundException::new);
  }

  @Transactional
  public ProfileImageResponseDto uploadProfileImage(UserInfo userInfo, ProfileImageRequestDto dto)
      throws IOException {
    validateImageFile(dto.getFile());
    Account account = findAccountById(userInfo.getAccountId());
    if (StringUtils.hasText(account.getProfileUrl())) {
      profileImageStorage.deleteFile(account.getProfileUrl());
    }
    String uploadKey = renameFileName(dto.getFile().getOriginalFilename());
    String uploadUrl = profileImageStorage.uploadFile(dto.getFile(), uploadKey);
    account.changeProfileImage(uploadUrl);
    return new ProfileImageResponseDto(uploadKey, uploadUrl);
  }

  private void validateImageFile(MultipartFile file) {
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
