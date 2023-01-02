package com.lengyue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.entity.ShoppingCart;
import com.lengyue.mapper.ShoppingCartMapper;
import com.lengyue.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
