package com.cnj.security.web;

import com.cnj.common.aspect.Log;
import com.cnj.common.i18n.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by hsdev-05 on 2019-02-21.
 */
@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    @Log
    public void login(@RequestParam(value = "error", required = false)String errorCode, @RequestParam(value = "logout",required = false)String logoutCode, HttpServletRequest request, HttpServletResponse response) {
        String message = null,prompt;
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        if (!StringUtils.isEmpty(errorCode)){
            message = MessageUtils.getMessage(errorCode);
        }else if(Objects.nonNull(logoutCode)){
            message = MessageUtils.getMessage("account.logout");
        }
        prompt = StringUtils.isEmpty(message)
                ? ""
                : String.format("<div id=\"message\" class=\"alert alert-error  alert-danger alert-dismissable\"> %s</div>",message);
        try (PrintWriter writer = response.getWriter()){
            writer.write(String.format("<html lang=\"en\"><head>\n    <meta charset=\"utf-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n    <meta name=\"description\" content=\"\">\n    <meta name=\"author\" content=\"\">\n    <title>登录</title>\n    <link  type=\"text/css\"  href=\"./css/bootstrap.min.css\" th:href=\"@{/css/bootstrap.min.css}\" rel=\"stylesheet\" >\n    <link type=\"text/css\" href=\"./css/signin.css\" th:href=\"@{/css/signin.css}\" rel=\"stylesheet\" crossorigin=\"anonymous\">\n</head>\n<body>\n<div class=\"container\">\n    <form class=\"form-signin\" method=\"post\" action=\"%s/login\">\n        <h3 class=\"form-signin-heading\">请登录</h3> %s        <p>\n            <label for=\"username\" class=\"sr-only\">用户名</label>\n            <input type=\"text\" id=\"username\" name=\"username\" class=\"form-control\" placeholder=\"请输入用户名\" required=\"\" autofocus=\"\">\n        </p>\n        <p>\n            <label for=\"password\" class=\"sr-only\">密码</label>\n            <input type=\"password\" id=\"password\" name=\"password\" class=\"form-control\" placeholder=\"请输入密码\" required=\"\">\n        </p>\n        <input name=\"_csrf\" type=\"hidden\" th:value=\"${_csrf.token}\">\n        <input type=\"submit\"  class=\"btn btn-lg btn-primary btn-block\" value=\"登录\">\n    </form>\n\n</div>\n</body>\n</html>", request.getContextPath(), prompt));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
