package cn.MrZhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.MrZhang.model.ProductInfo;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
