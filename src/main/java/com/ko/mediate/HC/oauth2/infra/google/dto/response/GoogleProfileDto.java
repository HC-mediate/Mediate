package com.ko.mediate.HC.oauth2.infra.google.dto.response;

import com.ko.mediate.HC.oauth2.domain.Profile;
import lombok.Getter;

@Getter
public class GoogleProfileDto {

    private String id;

    private String name;

    private String email;

    private String picture;

    public Profile toProfile() {
        return Profile.of(this.id, this.email, this.name);
    }
}
