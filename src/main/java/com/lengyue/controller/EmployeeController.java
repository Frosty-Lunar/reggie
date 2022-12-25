package com.lengyue.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lengyue.commons.Result;
import com.lengyue.entity.Employee;
import com.lengyue.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


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

    /**
     * 登录功能实现
     *
     * @param request  请求,主要用来将id存入session作用域
     * @param employee 员工
     * @return {@link Result}<{@link Employee}>
     */
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

    /**
     * 注销登录
     *
     * @param request 请求,主要用于清理session中的id
     * @return {@link Result}<{@link String}>
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employeeId");
        return Result.success("退出成功");
    }

    /**
     * 添加员工
     *
     * @param request  请求,获得更改员工ID，即登录用户ID
     * @param employee 员工
     * @return {@link Result}<{@link String}>
     */
    @PostMapping
    public Result<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        //默认密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return Result.success("添加员工成功");
    }

    /**
     * 分页查询列表
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @param name     员工姓名
     * @return {@link Result}
     */
    @GetMapping("/page")
    public Result employeeList(@Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name) {
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName, name);
        Page<Employee> employeePage = employeeService.page(pageInfo, queryWrapper);
        Result result = new Result();
        result.setCode(1);
        result.setData(employeePage);
        return result;
    }

    /**
     * 根据ID查询员工
     *
     * @param id id
     * @return {@link Result}
     */
    @GetMapping("{id}")
    public Result employeeList(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        Result result = new Result();
        result.setCode(1);
        result.setData(employee);
        return result;
    }

    /**
     * 更新员工
     *
     * @param request  请求
     * @param employee 员工
     * @return {@link Result}
     */
    @PutMapping
    public Result updateEmployee(@RequestBody Employee employee) {
        boolean flag = employeeService.updateById(employee);
        if (flag) {
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }
}
