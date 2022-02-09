package com.solo.rest.dao;

import com.solo.rest.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class EmployeeDao {

    private static Map<Integer, Employee> employeeList = null;

    static {

        employeeList = new HashMap<Integer, Employee>();

        employeeList.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1));
        employeeList.put(1002, new Employee(1002, "E-BB", "bb@163.com", 1));
        employeeList.put(1003, new Employee(1003, "E-CC", "cc@163.com", 0));
        employeeList.put(1004, new Employee(1004, "E-DD", "dd@163.com", 0));
        employeeList.put(1005, new Employee(1005, "E-EE", "ee@163.com", 1));

    }

    private static Integer initId = 1006;

    public void save(Employee employee){
        if(Objects.isNull(employee.getId())){
            employee.setId(initId++);
        }
        employeeList.put(employee.getId(),employee);
    }

    public Collection<Employee> getAll(){
        return employeeList.values();
    }

    public Employee get(Integer id){
        return employeeList.get(id);
    }

    public void delete(Integer id){
        employeeList.remove(id);
    }

}
