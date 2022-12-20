package com.lengyue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengyue.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工映射器
 *
 * @author 陌年
 * @date 2022/12/20
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
