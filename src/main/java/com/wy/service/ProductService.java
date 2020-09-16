package com.wy.service;

import com.wy.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
public interface ProductService extends IService<Product> {
 public List<Product> findAllByProductCategory(Integer type,Integer levelId);
 public boolean updateProduct(String type,Integer productId,Integer stock);
}
