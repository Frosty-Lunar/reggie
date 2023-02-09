package com.lengyue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengyue.entity.Category;

/**
 * 目录服务
 *
 * @author 陌年
 * @date 2023/02/09
 */
public interface CategoryService extends IService<Category> {
    /**
     * 通过ID删除目录
     *
     * @param id id
     */
    void removeById(Long id);
}
