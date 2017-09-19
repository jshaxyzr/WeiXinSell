package cn.MrZhang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Title:CartDTO
 * @Description: TODO 购物车 字段对象
 * @author MrZhang
 * @date 2017年9月19日 下午4:12:32 
 * @version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    /**
     * 商品id
     */
    private String productId;
    /**
    * 商品数量
    */
    private Integer productQuantity;

}
