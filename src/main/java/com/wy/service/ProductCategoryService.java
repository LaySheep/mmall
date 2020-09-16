package com.wy.service;

import com.wy.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.vo.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> getAllProductCategoryVO();
}
