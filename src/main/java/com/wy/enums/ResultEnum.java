package com.wy.enums;

import lombok.Getter;

public enum ResultEnum{
    USER_NOT_EXIST(0,"用户为空"),
    ORDER_NOT_EXIST(1,"订单为空"),
    USER_SAVE_FAIL(2,"用户保存失败");


    private Integer code;
    @Getter
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
