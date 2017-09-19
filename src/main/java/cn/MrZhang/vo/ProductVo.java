package cn.MrZhang.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * Title:ProductVo
 * @Description: TODO 商品 包含类目 vo
 * @author MrZhang
 * @date 2017年9月19日 上午10:51:20 
 * @param <T>
 * @version V1.0
 */
@Data
public class ProductVo {
    // 指定json序列化的名称
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> data;
}
