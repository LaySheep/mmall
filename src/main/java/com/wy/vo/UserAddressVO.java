package com.wy.vo;

import lombok.Data;

@Data
public class UserAddressVO {
    private Integer id;
    private Integer userId;
    private String address;
    private String remark;
    private Boolean isDefault;
}
