package cn.MrZhang.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * Title:ProductInfoVo
 * @Description: TODO 返回给前端的商品 VO类
 * @author MrZhang
 * @date 2017年9月19日 上午10:51:55 
 * @version V1.0
 */
@Data
public class ProductInfoVo {
    // 指定序列化的名称为id
    @JsonProperty("id")
    private String productId;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    /**
    * 描述
    */
    @JsonProperty("description")
    private String productDescription;
    /**
     * 小图标
     */
    @JsonProperty("icon")
    private String productIcon;
}
