package com.lengyue.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lengyue.commons.Result;
import com.lengyue.dto.DishDto;
import com.lengyue.entity.Category;
import com.lengyue.entity.Dish;
import com.lengyue.entity.DishFlavor;
import com.lengyue.service.CategoryService;
import com.lengyue.service.DishFlavorService;
import com.lengyue.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 盘控制器
 *
 * @author 陌年
 * @date 2022/12/26
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    DishService dishService;

    @Autowired
    CategoryService categoryService;
    @Autowired
    DishFlavorService dishFlavorService;

    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto) {
        log.info("dish dto：{}", dishDto);
        dishService.saveWithDishFlavor(dishDto);
        return Result.success("新增菜品成功");
    }

    @GetMapping("{id}")
    public Result<DishDto> save(@PathVariable("id") Long id) {
        log.info("id：{}", id);
        Dish dish = dishService.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        Category category = categoryService.getById(dish.getCategoryId());
        if (category != null) {
            dishDto.setCategoryName(category.getName());
        }
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> dishFlavors = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
        dishDto.setFlavors(dishFlavors);
        return Result.success(dishDto);
    }

    @GetMapping("/page")
    public Result<Page<DishDto>> page(@Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> pageDtoInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Dish::getName, name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, lambdaQueryWrapper);
        BeanUtils.copyProperties(pageInfo, pageDtoInfo, "records");
        List<DishDto> dishDtoList = pageInfo.getRecords().stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(pageInfo, dishDtoList);
        pageDtoInfo.setRecords(dishDtoList);
        return Result.success(pageDtoInfo);
    }

    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return Result.success("修改菜品分类成功！");
    }
}
