package com.lengyue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.commons.ErrorCode;
import com.lengyue.constant.EmployeeConstant;
import com.lengyue.entity.Employee;
import com.lengyue.exception.BusinessException;
import com.lengyue.mapper.EmployeeMapper;
import com.lengyue.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 员工业务类
 *
 * @author 陌年
 * @date 2022/12/20
 */
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Override
    public Employee employeeLogin(String username, String password, HttpServletRequest request) {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        queryWrapper.eq(Employee::getPassword, password);
        Employee employee = getOne(queryWrapper);
        if (employee == null) {
            log.info("员工登录失败，账号或密码错误！");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "员工登录失败，账号或密码错误！");
        }
        if (employee.getStatus() == 0) {
            log.error("员工登录失败，账户状态异常！");
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "员工登录失败，账户状态异常！");
        }
        request.getSession().setAttribute(EmployeeConstant.EMPLOYEE_LOGIN_ID, employee.getId());
        return employee;
    }
}
