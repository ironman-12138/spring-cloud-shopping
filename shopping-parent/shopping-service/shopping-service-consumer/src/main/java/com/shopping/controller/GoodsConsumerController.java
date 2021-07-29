package com.shopping.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import utils.Result;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "品牌服务消费者-接口管理")
@RestController
@RequestMapping("/brandConsumer")
@CrossOrigin
public class GoodsConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    /**
     * 查询所有品牌
     */
    @ApiOperation(value = "查询所有品牌", notes = "查询所有品牌")
    @PostMapping("/consumer/findAllBrandList")
    public Result findAllBrandList() {
        return restTemplate.postForObject(serverUrl + "/brand/findAllBrandList",null,Result.class);
    }

    /**
     * 测试
     */
    @ApiOperation(value = "测试", notes = "测试")
    @GetMapping("/consumer/string")
    public String getString() {
        return restTemplate.getForObject(serverUrl + "/brand/string",String.class);
    }

}
