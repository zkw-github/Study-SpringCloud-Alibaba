package com.student.zhaokangwei.exception;


import lombok.Data;

import javax.validation.ValidationException;

/**
 * 自定义一个基于Service层的验证异常
 */
@Data
public class ServiceValidationException extends ValidationException {

    private int state;

    public ServiceValidationException(String message, int state) {
        super(message);
        this.state = state;
    }

}