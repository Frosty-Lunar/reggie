package com.lengyue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengyue.entity.Category;

public interface CategoryService extends IService<Category> {
    void removeById(Long id);
}
