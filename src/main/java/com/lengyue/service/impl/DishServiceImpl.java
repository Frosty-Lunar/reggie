package com.lengyue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.dto.DishDto;
import com.lengyue.entity.Dish;
import com.lengyue.entity.DishFlavor;
import com.lengyue.exception.CustomException;
import com.lengyue.mapper.DishMapper;
import com.lengyue.service.DishFlavorService;
import com.lengyue.service.DishService;
import com.lengyue.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜服务impl
 *
 * @author 陌年
 * @date 2022/12/26
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 保存与菜味道
     *
     * @param dishDto 菜dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveWithDishFlavor(DishDto dishDto) {
        save(dishDto);
        //菜品ID
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 更新Dish与Flavor
     *
     * @param dishDto 菜dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWithFlavor(DishDto dishDto) {
        updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public void updateStatus(int status, Long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
            dishQueryWrapper.eq(Dish::getId, ids[i]);
            Dish dish = getOne(dishQueryWrapper);
            dish.setStatus(status);
            updateById(dish);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeWithFlavor(List<Long> ids) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Dish::getId, ids);
        lambdaQueryWrapper.eq(Dish::getStatus, 1);
        long count = count(lambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("菜品正在售卖中，不能删除！");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<DishFlavor> lambdaWrapper = new LambdaQueryWrapper<>();
        lambdaWrapper.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(lambdaWrapper);
    }
}
