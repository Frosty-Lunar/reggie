package com.lengyue.controller;

import com.lengyue.commons.Result;
import com.lengyue.entity.Category;
import com.lengyue.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 类别控制器
 *
 * @author 陌年
 * @date 2022/12/20
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public Result<String> save(@RequestBody Category category) {
        categoryService.save(category);
        log.info("分类添加成功！");
        return Result.success("分类添加成功！");
    }
}
