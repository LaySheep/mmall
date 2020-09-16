package com.wy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.entity.User;
import com.wy.entity.UserAddress;
import com.wy.service.CartService;
import com.wy.service.UserAddressService;
import com.wy.vo.UserAddressVO;
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
@RequestMapping("/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private CartService cartService;

    @GetMapping("/userAddressList")
    public ModelAndView userAddressList(HttpSession session){
       ModelAndView modelAndView=new ModelAndView();
        User user=(User)session.getAttribute("user");
        modelAndView.setViewName("userAddressList");
        modelAndView.addObject("carts",this.cartService.findAllCartVO(user.getId()));
        modelAndView.addObject("addressList",this.userAddressService.getAllAddress(user));
        return modelAndView;
    }

    @PostMapping("/add")
    @ResponseBody
    public String add(@RequestBody UserAddressVO userAddressVO){
        UserAddress userAddress=new UserAddress();
        userAddress.setUserId(userAddressVO.getUserId());
        userAddress.setAddress(userAddressVO.getAddress());
        userAddress.setRemark(userAddressVO.getRemark());
        boolean row = userAddressVO.getIsDefault();
        if (row){
            userAddress.setIsdefault(1);
        }else {
            userAddress.setIsdefault(0);
        }
        boolean save = this.userAddressService.save(userAddress);
        if (save)return "ok";
        return "fail";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<UserAddress> list(){
        return this.userAddressService.list();
    }

    @DeleteMapping("/del/{id}")
    @ResponseBody
    public String del(@PathVariable("id")Integer id){
        boolean result = this.userAddressService.removeById(id);
        if (result)return "ok";
        return "fail";
    }
    @GetMapping("/findById/{id}")
    @ResponseBody
    public UserAddressVO findById(@PathVariable("id") Integer id){
        UserAddress userAddress = this.userAddressService.getById(id);
        UserAddressVO userAddressVO=new UserAddressVO();
        if (userAddress.getIsdefault()==1) userAddressVO.setIsDefault(true);
        if (userAddress.getIsdefault()==0)userAddressVO.setIsDefault(false);
        userAddressVO.setId(userAddress.getId());
        userAddressVO.setUserId(userAddress.getUserId());
        userAddressVO.setAddress(userAddress.getAddress());
        userAddressVO.setRemark(userAddress.getRemark());
        return userAddressVO;
    }

    @PutMapping("/update")
    @ResponseBody
    public String update(@RequestBody UserAddressVO userAddressVO){
        UserAddress userAddress=new UserAddress();
        userAddress.setId(userAddressVO.getId());
        userAddress.setUserId(userAddressVO.getUserId());
        userAddress.setAddress(userAddressVO.getAddress());
        userAddress.setRemark(userAddressVO.getRemark());
        if (userAddressVO.getIsDefault()) userAddress.setIsdefault(1);
        else userAddress.setIsdefault(0);
        boolean result = this.userAddressService.updateById(userAddress);
        if (result) return "ok";
        return "fail";
    }
}

