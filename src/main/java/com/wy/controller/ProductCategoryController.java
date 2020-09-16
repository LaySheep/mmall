package com.wy.controller;


import com.wy.entity.User;
import com.wy.service.CartService;
import com.wy.service.ProductCategoryService;
import com.wy.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
@RequestMapping("/productCategory")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView=new ModelAndView();
        User user=(User) session.getAttribute("user");
        modelAndView.setViewName("main");
        modelAndView.addObject("categoryList",this.productCategoryService.getAllProductCategoryVO());
        List<CartVO> carts=new ArrayList<>();
        if (user!=null){
            carts=this.cartService.findAllCartVO(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }
}

