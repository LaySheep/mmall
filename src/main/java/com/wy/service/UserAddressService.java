package com.wy.service;

import com.wy.entity.User;
import com.wy.entity.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
public interface UserAddressService extends IService<UserAddress> {
    public List<UserAddress> getAllAddress(User user);
}
