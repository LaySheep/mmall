package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.entity.Cart;
import com.wy.entity.Product;
import com.wy.mapper.CartMapper;
import com.wy.mapper.ProductMapper;
import com.wy.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
   private ProductMapper productMapper;
    @Autowired
    private CartMapper cartMapper;
    @Override
    public List<Product> findAllByProductCategory(Integer type,Integer levelId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        String column="";
        switch (type){
            case 1:
                column="categorylevelone_id";
                break;
            case 2:
                column="categoryleveltwo_id";
                break;
            case 3:
                column="categorylevelthree_id";
                break;
        }
        queryWrapper.eq(column,levelId);
        return this.productMapper.selectList(queryWrapper);
    }

    @Override
    public boolean updateProduct(String type, Integer productId,Integer stock){
        Product product=this.productMapper.selectById(productId);
//        Integer val=null;
//        Integer temp=null;
        switch (type){
            case "sub":
             product.setStock(stock);
                break;
            case "add":
             product.setStock(stock);
                break;
        }
        int result = this.productMapper.updateById(product);
        if (result==1) return true;
        return false;
    }
}
