package com.cnj.security.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * Create by cnj on 2019-5-3
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(
        method={
                RequestMethod.GET,
                RequestMethod.POST
        },
        path = "/admin"
    )
    public String admin(){
        return "isAdmin";
    }

    @PreAuthorize("hasAnyAuthority('admin','user')")
    @RequestMapping(
            method={
                    RequestMethod.GET,
                    RequestMethod.POST
            },
            path = "/user"
    )
    public String user(){
        return "isUser";
    }


}
