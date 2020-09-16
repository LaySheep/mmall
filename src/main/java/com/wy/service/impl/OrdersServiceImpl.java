package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.entity.*;
import com.wy.mapper.*;
import com.wy.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.vo.OrderDetailVO;
import com.wy.vo.OrdersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Orders creat(String selectAddress, Float cost, User user,String address,String remark) {
        //判断新老地址
        if (selectAddress.equals("newAddress")){
            selectAddress=address;
            //存入userAddress表
            this.userAddressMapper.clearDefault(user.getId());
            UserAddress userAddress=new UserAddress();
            userAddress.setUserId(user.getId());
            userAddress.setAddress(selectAddress);
            userAddress.setRemark(remark);
            userAddress.setIsdefault(1);
            this.userAddressMapper.insert(userAddress);
        }
        //存储Orders
        Orders orders=new Orders();
        String seriaNumber = null;
        try {
            StringBuffer result = new StringBuffer();
            for(int i=0;i<32;i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriaNumber =  result.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
       orders.setSerialnumber(seriaNumber);
        orders.setCost(cost);
        orders.setUserId(user.getId());
        orders.setLoginName(user.getLoginName());
        orders.setUserAddress(selectAddress);
        this.ordersMapper.insert(orders);
        //存储ordersDetail
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        List<Cart> cartList=this.cartMapper.selectList(wrapper);
        for (Cart cart : cartList) {
           OrderDetail orderDetail=new OrderDetail();
           orderDetail.setOrderId(orders.getId());
            BeanUtils.copyProperties(cart,orderDetail);
            this.orderDetailMapper.insert(orderDetail);
        }
        //用户点击确认结算后清空购物车
        QueryWrapper wrapper1=new QueryWrapper();
        wrapper1.eq("user_id",user.getId());
        this.cartMapper.delete(wrapper1);
        return orders;
    }

    @Override
    public List<OrdersVO> getAllOrders(User user) {
        List<OrdersVO> ordersVOList=new ArrayList<>();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        //查询Orders封装OrdersVO
        List<Orders> ordersList=this.ordersMapper.selectList(wrapper);
        for (Orders orders : ordersList) {
            OrdersVO ordersVO=new OrdersVO();
            BeanUtils.copyProperties(orders,ordersVO);
            //通过orderId查询OrderDetail封装OrderDetailVO
            wrapper=new QueryWrapper();
            wrapper.eq("order_id",orders.getId());
            List<OrderDetail> orderDetailList=this.orderDetailMapper.selectList(wrapper);
            List<OrderDetailVO> orderDetailVOList=new ArrayList<>();
            for (OrderDetail orderDetail : orderDetailList) {
                OrderDetailVO orderDetailVO=new OrderDetailVO();
                BeanUtils.copyProperties(orderDetail,orderDetailVO);
                //通过productId查询对应的商品封装OrderDetailVO
                Product product=this.productMapper.selectById(orderDetail.getProductId());
                    BeanUtils.copyProperties(product,orderDetailVO);
                orderDetailVOList.add(orderDetailVO);
            }
            ordersVO.setOrderDetailVOList(orderDetailVOList);
            ordersVOList.add(ordersVO);
        }
        return ordersVOList;
    }
}
