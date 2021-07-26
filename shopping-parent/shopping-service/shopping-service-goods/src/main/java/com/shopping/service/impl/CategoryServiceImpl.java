package com.shopping.service.impl;

import com.shopping.domain.Category;
import com.shopping.mapper.CategoryMapper;
import com.shopping.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-07-26
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
