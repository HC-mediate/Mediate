package com.ko.mediate.HC.auth.domain;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.oauth2.domain.SocialType;
import com.ko.mediate.HC.tutoring.application.RoleType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_account")
@DynamicUpdate
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "socialType")
    private SocialType socialType;

    @Column(name = "socialId")
    private String socialId;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "is_activated", columnDefinition = "tinyint(1)")
    private Boolean isActivated;

    @Column(name = "role")
    @Getter(AccessLevel.PROTECTED)
    private String role;

    @Column(name = "phone_num")
    private String phoneNum;

    @Embedded
    private ProfileImage profileImage;

    @Column(name = "tutoring_location", length = 1200)
    private Point tutoringLocation;

    @Transient
    List<RoleType> roles = new ArrayList<>();

    @PrePersist
    void enumListToString() {
        this.role =
                roles.stream().map(RoleType::toString).collect(Collectors.joining(","));
    }

    @PostLoad
    void stringToEnumList() {
        this.roles =
                Arrays.stream(role.split(",")).map(RoleType::fromString)
                        .collect(Collectors.toList());
    }

    @Builder
    public Account(
            String email,
            String password,
            SocialType socialType,
            String socialId,
            String name,
            String nickname,
            String phoneNum,
            RoleType role,
            String profileKey,
            String profileUrl
    ) {
        this.email = email;
        this.password = password;
        this.socialType = socialType;
        this.socialId = socialId;
        this.name = name;
        this.nickname = nickname;
        this.isActivated = true;
        this.phoneNum = phoneNum;
        this.profileImage = new ProfileImage(profileKey, profileUrl);
        join(role);
    }

    public void changeProfileImage(String profileKey, String profileUrl) {
        this.profileImage = new ProfileImage(profileKey, profileUrl);
    }

    public boolean isDeactivated() {
        return !this.isActivated;
    }

    public boolean hasRole(RoleType role) {
        return roles.contains(role);
    }

    public void joinTutor(Point location) {
        this.tutoringLocation = location;
        join(RoleType.ROLE_TUTOR);
    }

    public void joinTutee(Point location) {
        this.tutoringLocation = location;
        join(RoleType.ROLE_TUTEE);
    }

    private boolean join(RoleType roleType) {
        if (roles.contains(roleType)) {
            throw new MediateIllegalStateException(ErrorCode.ALREADY_TUTOR_OR_TUTEE);
        }
        roles.add(roleType);
        return true;
    }
}
