package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:懒大王Smile
 * @Date: 2024/3/12
 * @Time: 16:39
 * @Description:
 */

public interface DishService {
    public void save(DishDTO dishDTO);

    PageResult queryPage(DishPageQueryDTO dishPageQueryDTO);

    void deleteDishById(List<Long> ids);

    DishVO GetDishById(Long id);

    void EditDishById(DishDTO dishDTO);
}
