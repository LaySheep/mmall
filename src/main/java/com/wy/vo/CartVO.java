package com.wy.vo;

import lombok.Data;

@Data
public class CartVO {
    private Integer id;
    private String name;
    private Float price;
    private Integer stock;
    private String fileName;
    private Integer quantity;
    private Float cost;
    private Integer productId;
}
