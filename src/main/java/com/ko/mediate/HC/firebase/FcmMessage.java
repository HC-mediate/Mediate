package com.ko.mediate.HC.firebase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {

  @JsonProperty(value = "validate_only")
  private boolean validateOnly;

  private Message message;

  @Builder
  @AllArgsConstructor
  @Getter
  public static class Message {
    private Notification notification;
    private String token;
  }

  @Builder
  @AllArgsConstructor
  @Getter
  public static class Notification {
    private String title;
    private String body;
    private String image;
  }
}
