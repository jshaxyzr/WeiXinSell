package cn.MrZhang.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Title:ProductInfo
 * @Description: TODO 商品详情
 * @author MrZhang
 * @date 2017年9月19日 下午2:34:13 
 * @version V1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo implements Serializable {
    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;
    /**
     * 库存
     */
    private Integer productStock;
    /**
    * 描述
    */
    private String productDescription;
    /**
     * 小图标
     */
    private String productIcon;
    /**
     * 状态  0 正常  1  下架
     */
    private Integer productStatus;
    /**
     * 类目编号
     */
    private Integer categoryType;

    private Date updateTime;

    private Date createdTime;

}
