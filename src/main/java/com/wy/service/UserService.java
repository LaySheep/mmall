package com.wy.service;

import com.wy.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
public interface UserService extends IService<User> {
    //注册
   public boolean register(User user);
   //登录
    public User login(User user);
}
