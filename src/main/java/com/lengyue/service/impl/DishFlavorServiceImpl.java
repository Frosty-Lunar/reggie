package com.lengyue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.entity.DishFlavor;
import com.lengyue.mapper.DishFlavorMapper;
import com.lengyue.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * 菜味道服务impl
 *
 * @author 陌年
 * @date 2022/12/26
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
