package com.lengyue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengyue.entity.User;
import com.lengyue.mapper.UserMapper;
import com.lengyue.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
