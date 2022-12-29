package com.lengyue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengyue.dto.DishDto;
import com.lengyue.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    void saveWithDishFlavor(DishDto dishDto);

    void updateWithFlavor(DishDto dishDto);

    void updateStatus(int status, Long[] ids);

    void removeWithFlavor(List<Long> ids);
}
