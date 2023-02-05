package com.lengyue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.commons.ErrorCode;
import com.lengyue.dto.DishDto;
import com.lengyue.dto.SetmealDto;
import com.lengyue.entity.Dish;
import com.lengyue.entity.Setmeal;
import com.lengyue.entity.SetmealDish;
import com.lengyue.exception.BusinessException;
import com.lengyue.mapper.SetmealMapper;
import com.lengyue.service.DishService;
import com.lengyue.service.SetmealDishService;
import com.lengyue.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private DishService dishService;

    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        long count = count(queryWrapper);
        if (count > 0) {
            //如果不能删除，抛出一个业务异常
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"套餐正在售卖中，不能删除");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);
    }

    @Override
    public void updateStatus(int status, List<Long> ids) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, ids);
        List<Setmeal> list = list(lambdaQueryWrapper);
        list = list.stream().map(item -> {
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());
        updateBatchById(list);
    }

    @Override
    public List<DishDto> getDishBySetMealId(Long id) {
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(id != null, SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(setmealDishLambdaQueryWrapper);
        List<DishDto> dishDtoList = list.stream().map(item -> {
            Long dishId = item.getDishId();
            Dish dish = dishService.getById(dishId);
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            dishDto.setCopies(item.getCopies());
            return dishDto;
        }).collect(Collectors.toList());
        return dishDtoList;
    }
}
