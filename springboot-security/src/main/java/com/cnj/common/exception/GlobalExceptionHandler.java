package com.cnj.common.exception;

import com.cnj.common.aspect.Log;
import com.cnj.common.util.HttpRequestUtil;
import com.cnj.common.i18n.MessageUtils;
import com.cnj.common.util.ResponseData;
import com.cnj.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by cnj on 2019-2-24
 */
@ControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    MessageUtils messageUtils;

    @Log
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseData handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.error("handleException :{}",ex);
        if (HttpRequestUtil.isJsonRequest(request)){
            return ResultUtils.error(ex);
        }
        try {
            request.getRequestDispatcher("/error").forward(request,response);
        } catch (IOException | ServletException e) {
            log.error("exception:{}",e);
        }
        return null;
    }
}