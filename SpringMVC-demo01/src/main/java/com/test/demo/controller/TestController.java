package com.test.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

// 已经被mvc的view-controller解析——见SpringMVC.xml
//    @RequestMapping("/")
//    public String index(){
//
//        return "index";
//    }

    @RequestMapping("/test_view")
    public String testView(){

        return "test_view";
    }

}
