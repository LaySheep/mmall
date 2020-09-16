package com.wy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.entity.User;
import com.wy.entity.UserAddress;
import com.wy.mapper.UserAddressMapper;
import com.wy.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> getAllAddress(User user) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        List<UserAddress> userAddressList=this.userAddressMapper.selectList(wrapper);
        return userAddressList;
    }
}
