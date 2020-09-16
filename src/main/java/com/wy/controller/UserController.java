package com.wy.controller;


import com.wy.entity.User;
import com.wy.enums.ResultEnum;
import com.wy.exception.MyException;
import com.wy.service.CartService;
import com.wy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
   private UserService userService;
    @Autowired
    private CartService cartService;

     @PostMapping("/register")
    public String register(User user){
       if (user==null){
             throw new MyException(ResultEnum.USER_NOT_EXIST.getMsg());
         }
         boolean register = userService.register(user);
         if (!register){
           throw new MyException(ResultEnum.USER_SAVE_FAIL.getMsg());
       }else {
           return "login";
       }
    }

    @PostMapping("/login")
    public String login(User user,HttpSession session){
        if (user==null){
            throw new MyException(ResultEnum.USER_NOT_EXIST.getMsg());
        }
        User result = userService.login(user);
        if (result!=null){
             session.setAttribute("user",result);
             return "redirect:/productCategory/list";
         }else {
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
         session.invalidate();
         return "login";
    }

    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        modelAndView.addObject("carts", this.cartService.findAllCartVO(user.getId()));
        return modelAndView;
    }
}

