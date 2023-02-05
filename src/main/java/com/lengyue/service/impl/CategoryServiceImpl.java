package com.lengyue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.commons.ErrorCode;
import com.lengyue.entity.Category;
import com.lengyue.entity.Dish;
import com.lengyue.entity.Setmeal;
import com.lengyue.exception.BusinessException;
import com.lengyue.mapper.CategoryMapper;
import com.lengyue.service.CategoryService;
import com.lengyue.service.DishService;
import com.lengyue.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 员工业务类
 *
 * @author 陌年
 * @date 2022/12/20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void removeById(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        long count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前分类下关联了菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        long count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前分类下关联了菜品，不能删除");
        }
        super.removeById(id);
    }
}
