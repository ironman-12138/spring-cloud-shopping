package com.shopping.controller;


import com.shopping.config.annotate.CustomParam;
import com.shopping.config.annotate.RequestJson;
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
    public Result<List<Brand>> findAllBrandList() {
        List<Brand> list = brandService.findAllBrandList();
        return Result.successToClient(list);
    }

    @ApiOperation(value = "测试", notes = "测试")
    @PostMapping("/test")
    public Result<String> test(@RequestJson("name") @RequestParam("name") String name) {
        System.out.println("name----->" + name);
        return Result.successToClient(name);
    }

    @ApiOperation(value = "测试2", notes = "测试2")
    @PostMapping("/test2")
    public Result<String> test2(@RequestBody Brand brand) {
        System.out.println("brand----->" + brand);
        return Result.successToClient(brand.toString());
    }

}

