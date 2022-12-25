package com.lengyue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengyue.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类别映射器
 *
 * @author 陌年
 * @date 2022/12/20
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
