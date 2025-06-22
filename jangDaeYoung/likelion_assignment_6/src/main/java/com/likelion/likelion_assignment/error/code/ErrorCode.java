package com.likelion.likelion_assignment.error.code;

import jakarta.persistence.GeneratedValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    /**
     * 404 NOT FOUND (찾을 수 없음)
     */
    CLIENT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 고객이 없습니다. clientId = ", "NOT_FOUND_404"),
    DELIVERY_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 배달이 없습니다. deliveryId = ", "NOT_FOUND_404"),

    /**
     * 500 INTERNAL SERVER ERROR (내부 서버 에러)
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러가 발생했습니다", "INTERNAL_SERVER_ERROR_500"),


    //유효성 검사
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "유효성 검사에 실패하였습니다.", "BAD_REQUEST_400"),

    ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.", "BAD_REQUEST_400"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.", "BAD_REQUEST_400"),
    NO_AUTHORIZATION_EXCEPTION(HttpStatus.BAD_REQUEST, "권한이 없습니다.", "BAD_REQUEST_400");


    private final HttpStatus httpStatus;
    private final String message;
    private final String code;


    public int getHttpStatusCode() {
        return httpStatus.value();
    }

}
