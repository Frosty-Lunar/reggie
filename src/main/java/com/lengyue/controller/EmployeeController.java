package com.lengyue.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lengyue.commons.Result;
import com.lengyue.entity.Employee;
import com.lengyue.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


/**
 * 员工控制器
 *
 * @author 陌年
 * @date 2022/12/20
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        String username = employee.getUsername();
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp == null) {
            return Result.error("未找到用户名");
        }
        if (!emp.getPassword().equals(password)) {
            return Result.error("密码错误");
        }
        if (emp.getStatus() == 0) {
            return Result.error("账户状态异常");
        }
        request.getSession().setAttribute("employeeId", emp.getId());
        return Result.success(emp);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employeeId");
        return Result.success("退出成功");
    }

    @PostMapping
    public Result<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        //默认密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long employeeId = (Long) request.getSession().getAttribute("employeeId");
        employee.setCreateUser(employeeId);
        employee.setUpdateUser(employeeId);
        employeeService.save(employee);
        return Result.success("添加员工成功");
    }
}
