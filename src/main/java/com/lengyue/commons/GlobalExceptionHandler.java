package com.lengyue.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理程序
 *
 * @author 陌年
 * @date 2022/12/20
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 异常处理程序
     *
     * @param ex SQL异常
     * @return {@link Result}<{@link String}>
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = "Duplicate entry";
        if (ex.getMessage().contains(message)) {
            String[] split = ex.getMessage().split("\\s+");
            message = split[2] + "已存在";
            return Result.error(message);
        }
        return Result.error("未知异常");
    }
}
