package com.cnj.security.config.handler;

import com.alibaba.fastjson.JSON;
import com.cnj.common.util.HttpRequestUtil;
import com.cnj.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Create by cnj on 2019-3-9
 */
@Component
@Slf4j
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (HttpRequestUtil.isJsonRequest(request)){
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(JSON.toJSONString(ResultUtils.ok()));
            out.flush();
            out.close();
        }else{
            super.setDefaultTargetUrl("/login?logout");
            super.onLogoutSuccess(request, response, authentication);
        }
    }
}
