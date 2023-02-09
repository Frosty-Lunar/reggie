package com.lengyue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengyue.dto.DishDto;
import com.lengyue.dto.SetmealDto;
import com.lengyue.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 保存套餐与菜品
     *
     * @param setmealDto setmeal dto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 移除套餐
     *
     * @param ids id
     */
    void removeWithDish(List<Long> ids);

    /**
     * 更新套餐状态
     *
     * @param status 状态
     * @param ids    id
     */
    void updateStatus(int status, List<Long> ids);

    /**
     * 获得套餐菜品通过id
     *
     * @param id id
     * @return {@link List}<{@link DishDto}>
     */
    List<DishDto> getDishBySetMealId(Long id);
}
