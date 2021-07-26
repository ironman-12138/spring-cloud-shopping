package com.shopping.service;

import com.shopping.domain.Brand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-07-26
 */
public interface BrandService extends IService<Brand> {

    /**
     * 查询所有品牌
     */
    List<Brand> findAllBrandList();

}
