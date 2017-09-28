package cn.MrZhang.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.MrZhang.enums.OrderStatusEnum;
import cn.MrZhang.enums.PayStatusEnum;
import cn.MrZhang.model.OrderDetail;
import cn.MrZhang.util.EnumUtil;
import cn.MrZhang.util.serializer.Date2LongSerializer;
import lombok.Data;

/**
 * 
 * Title:OrderDTO
 * @Description: TODO 各层之间数据传输
 * @author MrZhang
 * @date 2017年9月19日 下午3:06:54 
 * @version V1.0
 */
// 只包含非空属性 为空时不进行序列化 返回
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OrderDTO implements Serializable {

    private String orderId;
    /**
     * 买家姓名
     */
    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;
    /**
    * 买家微信Openid
    */
    private String buyerOpenid;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单状态 
     */
    private Integer orderStatus;
    /** 
     * 订单支付状态
     */
    private Integer payStatus;
    // 指定按哪个类进行序列化 redis 进行缓存存储的时候 会报错 因为redis配置中指定了序列化方式 Jackson2JsonRedisSerializer
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    // 附上初始值 保持接口返回信息的严谨性 及 list为空时不是null 而是一个空list
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
