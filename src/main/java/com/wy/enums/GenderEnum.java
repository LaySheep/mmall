package com.wy.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

public enum GenderEnum {
    MALE(1,"男"),
   FEMALE(0,"女");
    GenderEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    @EnumValue
    private Integer code;
    @Getter
    private String msg;
}
