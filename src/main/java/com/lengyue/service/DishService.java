package com.lengyue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengyue.dto.DishDto;
import com.lengyue.entity.Dish;

public interface DishService extends IService<Dish> {
    void saveWithDishFlavor(DishDto dishDto);

    void updateWithFlavor(DishDto dishDto);
}
