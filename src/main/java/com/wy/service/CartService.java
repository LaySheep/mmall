package com.wy.service;

import com.wy.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.entity.User;
import com.wy.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
public interface CartService extends IService<Cart> {
  public List<CartVO> findAllCartVO(Integer userId);
  public boolean updateCart(String type,Integer id,Integer productId,Integer quantity,Float cost);
  public void addCart(Integer id, Float price, Integer quantity, User user);
}
