package com.test.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {

    /**
     * 使用RESTFul模拟用户资源的增删改查
     * /user  GET  查询所有用户信息
     * /user/1  GET  根据用户Id查询所有用户信息
     * /user  POST  添加用户信息
     * /user/1  DELETE  删除用户信息
     * /user  PUT  修改用户信息
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getAllUser(){
        System.out.println("查询所有用户信息");
        return "success";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUserById(){
        System.out.println("根据Id查询用户信息");
        return "success";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String insertUser(String userName, String passWord){
        System.out.println("添加用户信息:" + userName +"," + passWord);
        return "success";
    }



}
