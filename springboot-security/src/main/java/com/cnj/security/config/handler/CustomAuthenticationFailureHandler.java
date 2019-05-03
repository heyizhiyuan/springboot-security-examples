package com.cnj.security.config.handler;

import com.alibaba.fastjson.JSONObject;
import com.cnj.common.util.HttpRequestUtil;
import com.cnj.common.i18n.MessageUtils;
import com.cnj.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hsdev-05 on 2019-02-22.
 */
@Component
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MessageUtils messageUtils;

    public String getExcepitonMessageCode(AuthenticationException exception){
        if (exception instanceof UsernameNotFoundException
            || exception instanceof BadCredentialsException) {
            return "account.notfound";
        }else if (exception instanceof BadCredentialsException){
            return "account.password.error";
        }else if (exception instanceof LockedException) {
            return "account.locked";
        } else if (exception instanceof CredentialsExpiredException) {
            return "account.credentials.expired";
        } else if (exception instanceof AccountExpiredException) {
            return "account.expired";
        } else if (exception instanceof DisabledException) {
            return "account.disabled";
        }
        return "account.login.failed";
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (HttpRequestUtil.isJsonRequest(httpServletRequest)){
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter out = httpServletResponse.getWriter();
            out.write(JSONObject.toJSONString(ResultUtils.error(messageUtils.getMessage(this.getExcepitonMessageCode(e)))));
            out.flush();
            out.close();
        }else{
            super.setDefaultFailureUrl(String.format("/login?error=%s",this.getExcepitonMessageCode(e)));
            super.setUseForward(false);
            super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
        }

    }
}
