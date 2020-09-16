package com.wy.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrdersVO {
    private String loginName;
    private String userAddress;
    private Float cost;
    private String serialnumber;
    private List<OrderDetailVO> orderDetailVOList;
}
