package com.likelion.likelion_assignment.common.exception;

import com.likelion.likelion_assignment.error.code.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String customMessage;

    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }
}
