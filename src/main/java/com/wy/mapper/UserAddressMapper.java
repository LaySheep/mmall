package com.wy.mapper;

import com.wy.entity.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wy
 * @since 2020-08-18
 */
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    public int clearDefault(Integer userId);
}
