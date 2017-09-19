package cn.MrZhang.enums;

import lombok.Getter;

/**
 * 
 * Title:PayStatusEnum
 * @Description: TODO 订单支付状态枚举
 * @author MrZhang
 * @date 2017年9月19日 下午2:13:25 
 * @version V1.0
 */
@Getter
public enum PayStatusEnum {

    WAIT(0, "未支付"), CANCEL(2, "退款中"), SUCCESS(1, "已支付");
    private Integer code;

    private String message;

    private PayStatusEnum(Integer code, String message) {
        // TODO Auto-generated constructor stub
        this.code = code;
        this.message = message;
    }
}
