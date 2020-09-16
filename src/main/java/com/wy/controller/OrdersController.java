package com.wy.controller;


import com.wy.entity.Orders;
import com.wy.entity.User;
import com.wy.service.CartService;
import com.wy.service.OrdersService;
import com.wy.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CartService cartService;

     @PostMapping("/creat")
     public ModelAndView creat(String selectAddress,Float cost, HttpSession session, String address,String remark){
         User user=(User) session.getAttribute("user");
         Orders orders = this.ordersService.creat(selectAddress, cost, user,address,remark);
         ModelAndView modelAndView=new ModelAndView();
         modelAndView.setViewName("settlement3");
         modelAndView.addObject("orders",orders);
         modelAndView.addObject("carts",this.cartService.findAllCartVO(user.getId()));
         return modelAndView;
     }

     @GetMapping("/ordersList")
     public ModelAndView ordersList(HttpSession session){
         User user=(User)session.getAttribute("user");
         ModelAndView modelAndView=new ModelAndView();
         modelAndView.setViewName("orderList");
         List<OrdersVO> list = this.ordersService.getAllOrders(user);
         modelAndView.addObject("ordersList",list);
         modelAndView.addObject("carts",this.cartService.findAllCartVO(user.getId()));
         return modelAndView;
     }
}

