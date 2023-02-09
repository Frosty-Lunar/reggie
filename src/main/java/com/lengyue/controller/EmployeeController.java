package com.lengyue.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lengyue.commons.BaseResponse;
import com.lengyue.commons.ErrorCode;
import com.lengyue.commons.Result;
import com.lengyue.commons.ResultUtils;
import com.lengyue.entity.Employee;
import com.lengyue.exception.BusinessException;
import com.lengyue.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 员工控制层
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
     * @param employee 员工对象
     * @return {@link Result}<{@link Employee}>
     */
    @PostMapping("/login")
    public BaseResponse<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        if (employee == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = employee.getUsername();
        String password = employee.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码不得为空！");
        }
        Employee employeeLogin = employeeService.employeeLogin(username, password, request);
        if (employeeLogin == null) {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR, "登录失败！");
        }
        return ResultUtils.success(employee);
    }

    /**
     * 注销登录
     *
     * @param request 请求,主要用于清理session中的员工ID
     * @return {@link Result}<{@link String}>
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employeeId");
        return Result.success("退出成功！");
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
    public Result<Page> employeeList(@Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name) {
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        Page<Employee> employeePage = employeeService.page(pageInfo, queryWrapper);
        return Result.success(employeePage);
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
