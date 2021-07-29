package com.shopping.controller;


import com.shopping.domain.Brand;
import com.shopping.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import utils.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author xcoder
 * @since 2021-07-26
 */
@Api(tags = "品牌-接口管理")
@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {

    @Resource
    private BrandService brandService;

    /**
     * 查询所有品牌
     */
    @ApiOperation(value = "查询所有品牌", notes = "查询所有品牌")
    @PostMapping("/findAllBrandList")
    public Result findAllBrandList() {
        List<Brand> list = brandService.findAllBrandList();
        return Result.ok().data("result",list);
    }

    @ApiOperation(value = "测试", notes = "测试")
    @GetMapping("/string")
    public String getString() {
        return "测试成功";
    }

}

