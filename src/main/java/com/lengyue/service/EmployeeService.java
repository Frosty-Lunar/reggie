package com.lengyue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengyue.entity.Employee;

import javax.servlet.http.HttpServletRequest;


/**
 * 员工服务
 *
 * @author 陌年
 * @date 2023/02/05
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * 员工登录
     *
     * @param username 用户名
     * @param password 密码
     * @param request  请求
     * @return {@link Employee}
     */
    Employee employeeLogin(String username, String password, HttpServletRequest request);
}
