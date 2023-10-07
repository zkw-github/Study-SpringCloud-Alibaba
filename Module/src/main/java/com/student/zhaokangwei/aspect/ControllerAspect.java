package com.student.zhaokangwei.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定义 Controller的切面
 */
@Slf4j
@Aspect
@Component
public class ControllerAspect {

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.student.zhaokangwei.controller.*.*(..))")
    private void method() {
    }


    /**
     * 定义前置通知
     *
     * @param joinPoint 加入点
     */
    @Before("method()")
    public void actionBefore(JoinPoint joinPoint) {
        List<ObjectError> errors = null;  //定义的BindingResult中的异常信息
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg; //定义检验绑定结果对象
                errors = result.getAllErrors().stream().distinct().collect(Collectors.toList());
                break;
            }
        }
        if (errors != null && errors.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (ObjectError error : errors) {
                log.error("JSR异常：" + error.getDefaultMessage());
                stringBuffer.append("," + error.getDefaultMessage());
            }
            stringBuffer.deleteCharAt(0);   //去掉第1个“,”
            //这里抛出这个异常的目的：是为了阻断程序的运行
            throw new ValidationException(stringBuffer.toString());
        }
    }

}