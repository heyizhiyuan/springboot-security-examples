package com.cnj.security.config;

import com.alibaba.fastjson.JSON;
import com.cnj.common.util.HttpRequestUtil;
import com.cnj.security.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Create by cnj on 2019-2-28
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //json登录
        if(HttpRequestUtil.isJsonRequest(request)){
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()){
                UserVO userVO = JSON.parseObject(IOUtils.toString(is,StandardCharsets.UTF_8), UserVO.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        userVO.getUsername(), userVO.getPassword());
            }catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            }finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
            //transmit it to UsernamePasswordAuthenticationFilter
        }else{
            return super.attemptAuthentication(request, response);
        }
    }
}
