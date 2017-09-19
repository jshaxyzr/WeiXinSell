package cn.MrZhang.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Title:OrderMaster
 * @Description: TODO 订单详情
 * @author MrZhang
 * @date 2017年9月19日 下午2:08:09 
 * @version V1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {
    @Id
    private String detailId;

    private String orderId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;
    /**
     *  商品数量
     */
    private Integer productQuantity;

    private String productIcon;

}
