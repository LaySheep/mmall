package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.entity.User;
import com.wy.enums.GenderEnum;
import com.wy.enums.ResultEnum;
import com.wy.exception.MyException;
import com.wy.mapper.UserMapper;
import com.wy.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    //注册
    @Override
    public boolean register(User user) {
        if (user.getSex()==1){
            user.setGender(GenderEnum.MALE);
        }
        if (user.getSex()==0){
            user.setGender(GenderEnum.FEMALE);
        }
        int result = userMapper.insert(user);
        if (result==1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        //验证用户名和密码
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("login_name",user.getLoginName());
        wrapper.eq("password",user.getPassword());
        User one = userMapper.selectOne(wrapper);
       return one;
    }
}
