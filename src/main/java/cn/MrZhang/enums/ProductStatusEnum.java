package cn.MrZhang.enums;

import lombok.Getter;

/**
 * 
 * Title:ProductStatusEnum
 * @Description: TODO 商品状态枚举
 * @author MrZhang
 * @date 2017年9月19日 下午2:12:52 
 * @version V1.0
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {

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
