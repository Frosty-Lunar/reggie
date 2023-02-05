package com.lengyue.exception;

import com.lengyue.commons.ErrorCode;

/**
 * 自定义业务异常类
 *
 * @author lengyue
 * @date 2023/02/01
 */
public class BusinessException extends RuntimeException {
    private final int code;
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
    public int getCode() {
        return code;
    }
}
