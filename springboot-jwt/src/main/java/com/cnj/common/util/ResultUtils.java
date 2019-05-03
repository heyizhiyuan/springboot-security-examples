package com.cnj.common.util;

import com.cnj.common.i18n.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create by cnj on 2019-2-24
 */
@Configuration
@Component
@Slf4j
public class ResultUtils {

    @Autowired
    private MessageUtils messageUtils;

    private static ResultUtils instance =null;

    @PostConstruct
    public void init(){
        instance = this;
        instance.messageUtils = messageUtils;
    }


    public static ResponseData.ResponseDataBuilder success(Object data){
        return ResponseData.builder()
                .code(1)
                .data(data)
                .status(ResponseData.OK)
                .message(instance.messageUtils.getMessage("success"));
    }

    public static ResponseData ok(Object data){
        return success(data).build();
    }

    public static ResponseData ok(){
        return ok(instance.messageUtils.getMessage("success"));
    }

    public static ResponseData ok(Object data,String message){
        return success(data).message(message).build();
    }

    public static ResponseData.ResponseDataBuilder failed(String message){
        return ResponseData.builder()
                .message(message)
                .status(ResponseData.ERROR)
                .code(0);
    }

    public static ResponseData error(Exception e){
        return failed(e.getMessage())
                .data(e).build();
    }

    public static ResponseData error(String message){
        return failed(message).build();
    }

    public static ResponseData error(){
        return failed(instance.messageUtils.getMessage("failed")).build();
    }
}
