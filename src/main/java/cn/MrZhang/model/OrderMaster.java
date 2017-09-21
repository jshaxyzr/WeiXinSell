package cn.MrZhang.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.MrZhang.enums.OrderStatusEnum;
import cn.MrZhang.enums.PayStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Title:OrderMaster
 * @Description: TODO 订单信息
 * @author MrZhang
 * @date 2017年9月19日 下午2:08:09 
 * @version V1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate // 该注解有 true false 2个属性 true 只更新我们修改过的字段
@DynamicInsert // 表示insert对象的时候,生成动态的insert语句,如果这个字段的值是null就不会加入到insert语句当中.默认false(注解后不指定值默认为true)。
public class OrderMaster implements Serializable {
    @Id
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
     * 订单状态  默认为新订单
     */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 
     * 订单支付状态 默认等待支付
     */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;
}
