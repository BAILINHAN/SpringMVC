package com.solo.rest.controller;

import com.solo.rest.bean.Employee;
import com.solo.rest.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
public class RestController {

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String getAllEmployee(Model model){

        Collection<Employee> employeeList = employeeDao.getAll();
        model.addAttribute("employeeList", employeeList);

        return "employee_list";
    }




}
