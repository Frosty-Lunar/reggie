package com.lengyue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengyue.dto.SetmealDto;
import com.lengyue.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);
}
