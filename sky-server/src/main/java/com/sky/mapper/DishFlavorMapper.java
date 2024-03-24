package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface DishFlavorMapper {



    void insertBatch(List<DishFlavor> flavors);


    void deleteFlavorById(List<Long> ids);

    @Select("select * from sky_take_out.dish_flavor where dish_id=#{id}")
    List<DishFlavor> getFlavorById(Long id);

    @Delete("delete from sky_take_out.dish_flavor where dish_id=#{id}")
    void deleteFlavor(Long id);

}
