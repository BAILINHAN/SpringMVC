package com.test.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ScopeController {

    //使用servletAPI向request域对象共享数据
    @RequestMapping("/testRequestByServletAPI")
    public String testRequestByServletAPI(HttpServletRequest request){

        request.setAttribute("testRequestScope", "hello,ServletAPI");
        return "success";
    }

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(ModelAndView modelAndView){
//
//        ModelAndView modelAndView = new ModelAndView();
        //处理模型数据，即向请求域request共享数据
        modelAndView.addObject("testRequestScope", "hello, ModelAndView");
        //设置视图名称
        modelAndView.setViewName("success");

        return modelAndView;
    }

    @RequestMapping("/testModel")
    public String testModel(Model model){

        model.addAttribute("testRequestScope", "hello, Model");

        return "success";

    }

    @RequestMapping("/testMap")
    public String testMap(Map map){

        map.put("testRequestScope", "hello, Map");

        return "success";

    }

    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap modelMap){

        modelMap.addAttribute("testRequestScope","hello, ModelMap");

        return "success";
    }

}
