package com.cnj.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hsdev-05 on 2019-02-22.
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

}
