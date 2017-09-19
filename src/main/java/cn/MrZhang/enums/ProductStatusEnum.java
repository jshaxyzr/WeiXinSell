package cn.MrZhang.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {

    UP(0, "在售"),

    Down(1, "下架");
    private Integer code;

    private String message;

    private ProductStatusEnum(Integer code, String message) {
        // TODO Auto-generated constructor stub
        this.code = code;
        this.message = message;
    }
}
