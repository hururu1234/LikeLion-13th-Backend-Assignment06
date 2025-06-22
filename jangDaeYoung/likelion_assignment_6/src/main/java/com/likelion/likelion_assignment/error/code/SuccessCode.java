package com.likelion.likelion_assignment.error.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {


    /**
     * 200 OK (성공)
     */
    GET_SUCCESS(HttpStatus.OK, "성공적으로 조회했습니다."),
    CLIENT_UPDATE_SUCCESS(HttpStatus.OK, "고객 정보가 성공적으로 수정되었습니다."),
    DELIVERY_UPDATE_SUCCESS(HttpStatus.OK, "배달 정보가 성공적으로 수정되었습니다."),
    CLIENT_DELETE_SUCCESS(HttpStatus.OK, "고객 정보가 성공적으로 삭제되었습니다."),
    DELIVERY_DELETE_SUCCESS(HttpStatus.OK, "배달 정보가 성공적으로 삭제되었습니다."),

    /**
     * 201 CREATED (생성 성공)
     */
    CLIENT_SAVE_SUCCESS(HttpStatus.CREATED, "고객 정보가 성공적으로 생성되었습니다."),
    DELIVERY_SAVE_SUCCESS(HttpStatus.CREATED, "배달 정보기 성공적으로 생성되었습니다."),

    CLIENT_JOIN_SUCCESS(HttpStatus.CREATED, "회원가입에 성공하였습니다."),
    CLIENT_LOGIN_SUCCESS(HttpStatus.CREATED, "로그인에 성공하였습니다.");

    private final HttpStatus httpStatus;   // HTTP 상태 코드 ex) 200, 201
    private final String message;          // 응답 메세지

    public int getHttpStatusCode() {       // HTTP 상태 코드에서 404와 같은 숫자 값만 반환해 주기 위한 메소드
        return httpStatus.value();
    }


}
