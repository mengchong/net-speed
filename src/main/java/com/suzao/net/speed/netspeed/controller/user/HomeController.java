package com.suzao.net.speed.netspeed.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @name HomeController
 * @author mc
 * @date 2022/4/9 20:37
 * @version 1.0
 **/
@Controller
@RequestMapping(value = "/vesapp/login")
public class HomeController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
