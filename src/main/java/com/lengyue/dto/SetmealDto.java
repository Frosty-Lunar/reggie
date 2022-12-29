package com.lengyue.dto;

import com.lengyue.entity.Setmeal;
import com.lengyue.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
