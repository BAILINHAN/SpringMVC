package com.solo.dispatcher.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/testInterceptor")
    public String testInterceptor(){

        return "success";
    }

}
