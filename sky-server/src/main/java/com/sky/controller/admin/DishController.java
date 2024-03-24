package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:懒大王Smile
 * @Date: 2024/3/12
 * @Time: 16:34
 * @Description:
 */

@RestController
@RequestMapping("/admin/dish")
@Api("菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品 {}",dishDTO);
        dishService.save(dishDTO);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> queryPage(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询 {}",dishPageQueryDTO);
        PageResult pageResult=dishService.queryPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("菜品删除")
    public Result deleteDishById(@RequestParam List<Long> ids){
        log.info("菜品删除 {}",ids);
        dishService.deleteDishById(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> GetDishById(@PathVariable Long id){
        log.info("根据id查询菜品 {}",id);
        DishVO dishVO=dishService.GetDishById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("根据id修改菜品")
    public Result EditDishById(@RequestBody DishDTO dishDTO){
        log.info("根据id修改菜品 {}",dishDTO);
        dishService.EditDishById(dishDTO);
        return Result.success();
    }
}
