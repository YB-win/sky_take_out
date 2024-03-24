package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:懒大王Smile
 * @Date: 2024/3/12
 * @Time: 20:46
 * @Description:
 */

@Mapper
public interface SetMealDishMapper {


    List<Long> getSetMealIdsByDishIds(List<Long> ids);
}
