package com.wy.controller;


import com.wy.entity.User;
import com.wy.service.CartService;
import com.wy.service.ProductCategoryService;
import com.wy.service.ProductService;
import com.wy.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/product")
public class ProductController {
    @Autowired
   private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;

    @GetMapping("/list/{type}/{id}")
    public ModelAndView list(@PathVariable("type")Integer type,
                             @PathVariable("id")Integer id,
                             HttpSession session){
        ModelAndView modelAndView=new ModelAndView();
        User user=(User) session.getAttribute("user");
        modelAndView.setViewName("productList");
        modelAndView.addObject("productList",this.productService.findAllByProductCategory(type, id));
        modelAndView.addObject("categoryList",this.productCategoryService.getAllProductCategoryVO());
        List<CartVO> carts=new ArrayList<>();
        if (user!=null){
            carts=this.cartService.findAllCartVO(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }
    @GetMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable("id") Integer id,HttpSession session){
        ModelAndView modelAndView=new ModelAndView();
        User user=(User) session.getAttribute("user");
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("productDetail",this.productService.getById(id));
        modelAndView.addObject("categoryList",this.productCategoryService.getAllProductCategoryVO());
        List<CartVO> carts=new ArrayList<>();
        if (user!=null){
            carts=this.cartService.findAllCartVO(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }

    @PostMapping("/updateProduct/{type}/{productId}/{stock}")
    @ResponseBody
    public String updateProduct(
            @PathVariable("type")String type,
            @PathVariable("productId")Integer productId,
            @PathVariable("stock")Integer stock){
        boolean result = this.productService.updateProduct(type, productId,stock);
        if (result) return "success";
        return "fail";
    }
}

