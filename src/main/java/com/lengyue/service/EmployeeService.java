package com.lengyue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengyue.entity.Employee;

import javax.servlet.http.HttpServletRequest;


public interface EmployeeService extends IService<Employee> {
    Employee employeeLogin(String username, String password, HttpServletRequest request);
}
