package com.cnj.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.xml.ws.Action;

/**
 * Create by cnj on 2019-2-25
 */
@Slf4j
@Configuration
public class SpringUtils implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass){
        try{
            return applicationContext.getBean(tClass);
        }catch (BeansException e){
            log.warn("未得到对象，clazz:"+tClass, e);
        }
        return null;
    }

    public static <T> T getBean(String name,Class<T> tClass){
        try{
            return applicationContext.getBean(tClass);
        }catch (BeansException e){
            log.warn(String.format("未得到对象，clazz:%s,name:%s",tClass,name), e);
        }
        return null;
    }


}
