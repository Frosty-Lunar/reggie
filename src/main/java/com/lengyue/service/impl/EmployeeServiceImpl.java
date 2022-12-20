package com.lengyue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.entity.Employee;
import com.lengyue.mapper.EmployeeMapper;
import com.lengyue.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * 员工业务类
 *
 * @author 陌年
 * @date 2022/12/20
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
