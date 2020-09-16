package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.entity.Cart;
import com.wy.entity.Product;
import com.wy.entity.User;
import com.wy.mapper.CartMapper;
import com.wy.mapper.ProductMapper;
import com.wy.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<CartVO> findAllCartVO(Integer userId) {
        List<CartVO> result=new ArrayList<>();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<Cart> cartList=this.cartMapper.selectList(wrapper);
        CartVO cartVO=null;
        for (Cart cart : cartList) {
            Product product=this.productMapper.selectById(cart.getProductId());
            cartVO=new CartVO();
            BeanUtils.copyProperties(product,cartVO);
            BeanUtils.copyProperties(cart,cartVO);
            result.add(cartVO);
        }
        return result;
    }

    @Override
    public boolean updateCart(String type, Integer id, Integer productId, Integer quantity, Float cost) {
        //修改购物车里商品数量--quantity和小计--cost
        Cart cart=this.cartMapper.selectById(id);
        Product product=this.productMapper.selectById(productId);
        Integer val=null;
        //根据type判断是加法还是减法
        //修改商品库存
        switch (type){
            case "sub":
                    val=cart.getQuantity()-quantity;
                    product.setStock(product.getStock()+val);
                break;
            case "add":
                    val=quantity-cart.getQuantity();
                    product.setStock(product.getStock()-val);
                break;
        }
        cart.setQuantity(quantity);
        cart.setCost(cost);
        int cartRow = this.cartMapper.updateById(cart);
        int productRow = this.productMapper.updateById(product);
        if (cartRow==1 && productRow==1)return true;
        return false;
    }

    @Override
    public void addCart(Integer id, Float price, Integer quantity, User user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        wrapper.eq("product_id",id);
        Cart cart= this.cartMapper.selectOne(wrapper);
        Integer val=null;
        if (cart!=null) {
                if ((user.getId().equals(cart.getUserId())) && (id.equals(cart.getProductId()))) {
                    Product product=this.productMapper.selectById(cart.getProductId());
                     price=product.getPrice();
                    val=cart.getQuantity();
                    cart.setQuantity(quantity+val);
                    Float cost=(quantity+val)*price;
                    cart.setCost(cost);
                    this.cartMapper.updateById(cart);
                }else {
                    cart=new Cart();
                    Float cost = price*quantity;
                    cart.setProductId(id);
                    cart.setCost(cost);
                    cart.setUserId(user.getId());
                    cart.setQuantity(quantity);
                    this.cartMapper.insert(cart);
                }
        } else {
            Cart newCart=new Cart();
            Float cost = price*quantity;
            newCart.setProductId(id);
            newCart.setCost(cost);
            newCart.setUserId(user.getId());
            newCart.setQuantity(quantity);
            this.cartMapper.insert(newCart);
        }
    }
}
