package com.wy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.entity.Cart;
import com.wy.entity.Product;
import com.wy.entity.User;
import com.wy.entity.UserAddress;
import com.wy.service.CartService;
import com.wy.service.ProductService;
import com.wy.service.UserAddressService;
import com.wy.vo.CartVO;
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
@RequestMapping("/cart")
public class CartController {
    @Autowired
   private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/add/{id}/{price}/{quantity}")
    public String add(
            @PathVariable("id") Integer id,
            @PathVariable("price") Float price,
            @PathVariable("quantity") Integer quantity,
            HttpSession session){
            User user=(User)session.getAttribute("user");
            this.cartService.addCart(id, price, quantity, user);
            return "redirect:/cart/cartVO";
    }

    @GetMapping("/cartVO")
    public ModelAndView cartVO(HttpSession session){
        ModelAndView modelAndView=new ModelAndView();
        User user=(User) session.getAttribute("user");
        modelAndView.setViewName("settlement1");
        modelAndView.addObject("carts",this.cartService.findAllCartVO(user.getId()));
        return  modelAndView;
    }

    @PostMapping("/updateCart/{type}/{id}/{productId}/{quantity}/{cost}")
    @ResponseBody
    public String updateCart(
            @PathVariable("type")String type,
            @PathVariable("id")Integer id,
            @PathVariable("productId")Integer productId,
            @PathVariable("quantity")Integer quantity,
            @PathVariable("cost")Float cost
    ){
        boolean result = this.cartService.updateCart(type, id, productId, quantity, cost);
        if (result) return "success";
        return "fail";
    }

    @GetMapping("/removeCart/{id}")
    public synchronized String removeCart(@PathVariable("id")Integer id){
        //删除购物车商品后增加库存
        Cart cart = this.cartService.getById(id);
        Product product = this.productService.getById(cart.getProductId());
        product.setStock(cart.getQuantity()+product.getStock());
        this.productService.updateById(product);
        //删除购物车商品
        this.cartService.removeById(id);
        return "redirect:/cart/cartVO";
    }

    @GetMapping("/goToSettlement2")
    public ModelAndView goToSettlement2(HttpSession session){
        User user=(User)session.getAttribute("user");
        //获取购物车记录
        List<CartVO> cartVOList=this.cartService.findAllCartVO(user.getId());
        //地址记录
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        List<UserAddress> userAddressList=this.userAddressService.list(wrapper);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("settlement2");
        modelAndView.addObject("cartVOList",cartVOList);
        modelAndView.addObject("userAddressList",userAddressList);
        modelAndView.addObject("carts",this.cartService.findAllCartVO(user.getId()));
        return modelAndView;
    }
}

