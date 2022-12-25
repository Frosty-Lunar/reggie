package com.lengyue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.entity.Dish;
import com.lengyue.mapper.DishMapper;
import com.lengyue.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {
}
