package com.solo.rest.controller;

import com.solo.rest.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestController {

    @Autowired
    private EmployeeDao employeeDao;



}
