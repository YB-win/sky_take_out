package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Employee;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:懒大王Smile
 * @Date: 2024/3/12
 * @Time: 16:39
 * @Description:
 */

@Slf4j
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    public void EditDishById(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.EditDishById(dish);
        //先删除原先的口味数据，在插入新的口味数据
        dishFlavorMapper.deleteFlavor(dish.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors !=null&& flavors.size()>0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public DishVO GetDishById(Long id) {
        DishVO dishVO = new DishVO();
        Dish dish = dishMapper.selectDishById(id);
        List<DishFlavor> flavors= dishFlavorMapper.getFlavorById(id);
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Transactional
    @Override
    public void deleteDishById(List<Long> ids) {
        //判断菜品是否起售
        for (Long id :ids){
            Dish dish=dishMapper.selectDishById(id);
            if (dish.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断菜品是否关联套餐
        List<Long> setMealIds=setMealDishMapper.getSetMealIdsByDishIds(ids);
        if (setMealIds!=null&&setMealIds.size()>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

//        for (Long id :ids){
//            //删除菜品表数据
//            dishMapper.deleteDishById(id);
//            //删除菜品关联的口味数据
//            dishFlavorMapper.deleteFlavorById(id);
//        }

            //删除菜品表数据
            dishMapper.deleteDishById(ids);
            //删除菜品关联的口味数据
            dishFlavorMapper.deleteFlavorById(ids);



    }

    @Override
    public PageResult queryPage(DishPageQueryDTO dishPageQueryDTO) {

        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        Page<DishVO> page=dishMapper.queryPage(dishPageQueryDTO);

        return new PageResult(page.getTotal(),page.getResult());

    }

    @Transactional//事务注解
    @Override
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //向菜品表插入一条数据
        dishMapper.insert(dish);
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!=null && flavors.size()>0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            //向口味表插入多条数据
            dishFlavorMapper.insertBatch(flavors);

        }
    }
}
