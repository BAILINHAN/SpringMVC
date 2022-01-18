package com.test.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/testThymeleafView")
    public String testThymeleafView(){

        return "success";
    }

    @RequestMapping("/testForward")
    public String testForward(){

        //转发到testThymeleafView接口路径
        return "forward:/testThymeleafView";
    }

    @RequestMapping("/testRedirect")
    public String testRedirect(){

        //重定向到testThymeleafView
        return "redirect:/testThymeleafView";
    }

}

