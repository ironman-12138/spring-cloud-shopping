package com.shopping.service.impl;

import com.shopping.domain.Brand;
import com.shopping.mapper.BrandMapper;
import com.shopping.service.BrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-07-26
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

}
