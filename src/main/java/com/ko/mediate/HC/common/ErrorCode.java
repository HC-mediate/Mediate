package com.ko.mediate.HC.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    EMAIL_ALREADY_EXIST("중복된 이메일입니다.", HttpStatus.BAD_REQUEST),
    ENTITY_NOT_FOUND("해당 ID의 엔티티가 없습니다.", HttpStatus.BAD_REQUEST),
    NO_REFRESH_TOKEN("리프레쉬 토큰이 없습니다.", HttpStatus.FORBIDDEN),
    ALREADY_TUTOR_OR_TUTEE("이미 튜터이거나 튜티입니다.", HttpStatus.BAD_REQUEST),
    IMAGE_EXCEED("이미지는 최대 5개까지 첨부가능합니다.", HttpStatus.NO_CONTENT),
    NO_ACCESS_TOKEN("액세스 토큰이 없습니다.", HttpStatus.UNAUTHORIZED),
    TUTORING_NO_WAIT_STATE("튜터링이 수락 대기 중 상태가 아닙니다.", HttpStatus.BAD_REQUEST),
    NO_TUTORING_MEMBER("튜터링 멤버가 아닙니다.", HttpStatus.BAD_REQUEST),
    TUTORING_CANCEL_STATE("학습 중이거나 대기 중이어야 합니다.", HttpStatus.BAD_REQUEST),
    INCORRECT_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    IMAGE_CONVERT_FAILED("유효하지 않은 이미지입니다.", HttpStatus.BAD_REQUEST),
    UNSUPPORT_IMAGE_TYPE("지원하지 않는 미디어타입입니다.", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    NO_ACCOUNT_EXISTS("가입 이력이 없습니다. 회원가입을 진행해 주세요.", HttpStatus.BAD_REQUEST),
    NICKNAME_ALREADY_EXIST("이미 존재하는 닉네임입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ACCESS("해당 작업을 수행할 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
    NULL_PROPERTY("엔티티의 프로퍼티는 null 값을 가질 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DEACTIVATED_ACCOUNT("비활성화된 계정입니다.", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("만료된 토큰입니다.", HttpStatus.FORBIDDEN);

    private final String description;
    private final HttpStatus status;

    public String getCode() {
        return String.format("ERR_%d", this.ordinal());
    }

    ErrorCode(String description, HttpStatus status) {
        this.description = description;
        this.status = status;
    }
}
