package com.lengyue.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lengyue.commons.Result;
import com.lengyue.entity.Category;
import com.lengyue.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/page")
    public Result<Page<Category>> queryCategoryByPage(@Param("page") int page, @Param("pageSize") int pageSize) {
        Page<Category> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        Page<Category> categoryPage = categoryService.page(pages, wrapper);
        return Result.success(categoryPage);
    }

    @DeleteMapping
    public Result<String> deleteCategoryById(@Param("id") Long id) {
        categoryService.removeById(id);
        return Result.success("分类信息删除成功");

    }

    @PutMapping
    public Result<String> modifyCategory(@RequestBody Category category) {
        boolean flag = categoryService.updateById(category);
        if (flag) {
            return Result.success("更新成功！");
        }
        return Result.error("更新失败！");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return Result.success(list);
    }
}
