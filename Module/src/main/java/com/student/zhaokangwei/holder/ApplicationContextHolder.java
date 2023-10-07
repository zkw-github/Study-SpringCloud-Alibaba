package com.student.zhaokangwei.holder;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 上下文持有类
 */
@Slf4j
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
        log.info("完成setApplicationContext................");


    }
    public static <T>T getBean(String beanName){
        return (T) applicationContext.getBean(beanName);

    }
}
