package com.wy.vo;

import lombok.Data;

@Data
public class OrderDetailVO {
    private String name;
    private String fileName;
    private Float price;
    private Integer quantity;
    private Float cost;
}
