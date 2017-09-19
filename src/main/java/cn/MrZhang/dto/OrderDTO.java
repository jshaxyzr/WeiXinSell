package cn.MrZhang.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.MrZhang.model.OrderDetail;
import lombok.Data;

/**
 * 
 * Title:OrderDTO
 * @Description: TODO 各层之间数据传输
 * @author MrZhang
 * @date 2017年9月19日 下午3:06:54 
 * @version V1.0
 */
@Data
public class OrderDTO {

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

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
