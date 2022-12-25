package com.lengyue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.entity.Category;
import com.lengyue.mapper.CategoryMapper;
import com.lengyue.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 员工业务类
 *
 * @author 陌年
 * @date 2022/12/20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
