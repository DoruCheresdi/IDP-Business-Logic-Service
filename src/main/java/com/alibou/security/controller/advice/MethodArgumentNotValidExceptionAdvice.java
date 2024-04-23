package com.alibou.security.controller.advice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class MethodArgumentNotValidExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MethodArgumentNotValidException userEmailNotUniqueHandler(MethodArgumentNotValidException ex) {
        return ex;
    }
}
