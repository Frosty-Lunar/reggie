package com.lengyue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengyue.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
