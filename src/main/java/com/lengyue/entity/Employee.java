package com.lengyue.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工类
 *
 * @author 陌年
 * @date 2022/12/20
 */
@Data
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     * 状态(0-禁用 1-启用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建用户
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新用户
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
