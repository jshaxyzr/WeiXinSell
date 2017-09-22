package cn.MrZhang.enums;

import lombok.Getter;

/**
 * 
 * Title:OrderStatusEnum
 * @Description: TODO 订单状态枚举
 * @author MrZhang
 * @date 2017年9月19日 下午2:13:12 
 * @version V1.0
 */
@Getter
public enum OrderStatusEnum implements CodeEnum  {

    NEW(0, "新订单"), CANCEL(2, "取消"), FINSHED(1, "完成");
    private Integer code;

    private String message;

    private OrderStatusEnum(Integer code, String message) {
        // TODO Auto-generated constructor stub
        this.code = code;
        this.message = message;
    }
}
