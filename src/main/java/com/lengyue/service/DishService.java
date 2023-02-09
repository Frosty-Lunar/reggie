package com.lengyue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengyue.dto.DishDto;
import com.lengyue.entity.Dish;

import java.util.List;

/**
 * 菜品服务
 *
 * @author 陌年
 * @date 2023/02/09
 */
public interface DishService extends IService<Dish> {
    /**
     * 保存菜品与口味
     *
     * @param dishDto 菜dto
     */
    void saveWithDishFlavor(DishDto dishDto);

    /**
     * 更新菜品口味
     *
     * @param dishDto 菜dto
     */
    void updateWithFlavor(DishDto dishDto);

    /**
     * 更新菜品状态
     *
     * @param status 状态
     * @param ids    id
     */
    void updateStatus(int status, Long[] ids);

    /**
     * 移除菜品
     *
     * @param ids id
     */
    void removeWithFlavor(List<Long> ids);
}
