package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.entity.ProductCategory;
import com.wy.mapper.ProductCategoryMapper;
import com.wy.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.service.ProductService;
import com.wy.vo.ProductCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    @Autowired
    private ProductService productService;
    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategoryVO> getAllProductCategoryVO() {
        //查询所有的一级分类
        List<ProductCategoryVO> result=new ArrayList<>();
       List<ProductCategory> levelOneList=this.getProductCategoryByType(1,0);
       //转VO
        ProductCategoryVO productCategoryVOOne;
        int num=0;
        for (ProductCategory categoryLevelOne : levelOneList) {
            productCategoryVOOne=new ProductCategoryVO();
            BeanUtils.copyProperties(categoryLevelOne,productCategoryVOOne);
            productCategoryVOOne.setBanner("banner"+num+".png");
            productCategoryVOOne.setTop("top"+num+".png");
            num++;
            productCategoryVOOne.setProductList(this.productService.findAllByProductCategory(1,productCategoryVOOne.getId()));
            //查询一级分类对应的二级分类
            List<ProductCategory> levelTwoList=this.getProductCategoryByType(2,productCategoryVOOne.getId());
            ProductCategoryVO productCategoryVOTwo;
            List<ProductCategoryVO> voTwoList=new ArrayList<>();
            for (ProductCategory categoryTwo : levelTwoList) {
                productCategoryVOTwo=new ProductCategoryVO();
                //转VO
                BeanUtils.copyProperties(categoryTwo,productCategoryVOTwo);
                //查询二级分类对应的三级分类
                List<ProductCategory> levelThreeList=this.getProductCategoryByType(3,productCategoryVOTwo.getId());
                //转VO
                List<ProductCategoryVO> voThreeList=new ArrayList<>();
                ProductCategoryVO productCategoryVOThree;
                for (ProductCategory categoryThree : levelThreeList) {
                    productCategoryVOThree=new ProductCategoryVO();
                    BeanUtils.copyProperties(categoryThree,productCategoryVOThree);
                    voThreeList.add(productCategoryVOThree);
                }
                //设置children
               productCategoryVOTwo.setChildren(voThreeList);
                voTwoList.add(productCategoryVOTwo);
            }
            //设置children
            productCategoryVOOne.setChildren(voTwoList);
            result.add(productCategoryVOOne);
        }
        return result;
    }
    public List<ProductCategory> getProductCategoryByType(Integer type,Integer parentId){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("type",type);
        queryWrapper.eq("parent_id",parentId);
        return this.productCategoryMapper.selectList(queryWrapper);
    }
}
