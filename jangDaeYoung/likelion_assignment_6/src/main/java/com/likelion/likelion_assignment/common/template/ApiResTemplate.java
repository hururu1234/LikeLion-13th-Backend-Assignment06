package com.likelion.likelion_assignment.common.template;

import com.likelion.likelion_assignment.error.code.ErrorCode;
import com.likelion.likelion_assignment.error.code.SuccessCode;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ApiResTemplate<T> {

    private final int code;
    private final String message;
    private T data;

    //데이터 없는 성공 응답
    public static ApiResTemplate successWithNoContent(SuccessCode successCode){
        return new ApiResTemplate<>(successCode.getHttpStatusCode(), successCode.getMessage());
    }

    //데이터 포함 성공 응답
    public static <T> ApiResTemplate<T> successResponse(SuccessCode successCode, T data){
        return new ApiResTemplate<>(successCode.getHttpStatusCode(), successCode.getMessage(), data);
    }

    //에러응답
    public static ApiResTemplate errorResponse(ErrorCode errorCode, String customMessage) {
        return new ApiResTemplate<>(errorCode.getHttpStatusCode(), customMessage);
    }

}
