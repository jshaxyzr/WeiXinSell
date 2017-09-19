package cn.MrZhang.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.MrZhang.model.ProductInfo;

public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /**
     * 
    * @Title: findUpAll
    * @Description: TODO 所有在售的商品
    * @return List<ProductInfo>
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);
}
