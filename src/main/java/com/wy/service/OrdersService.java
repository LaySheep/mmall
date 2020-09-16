package com.wy.service;

import com.wy.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.entity.User;
import com.wy.vo.OrdersVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
public interface OrdersService extends IService<Orders> {
   public Orders creat(String selectAddress, Float cost, User user,String address,String remark);
   public List<OrdersVO> getAllOrders(User user);
}
