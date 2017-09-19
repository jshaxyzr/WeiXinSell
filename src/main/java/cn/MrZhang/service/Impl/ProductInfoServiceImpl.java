package cn.MrZhang.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.MrZhang.enums.ProductStatusEnum;
import cn.MrZhang.model.ProductInfo;
import cn.MrZhang.repository.ProductInfoRepository;
import cn.MrZhang.service.ProductInfoService;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        // TODO Auto-generated method stub
        return productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        // TODO Auto-generated method stub
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        // TODO Auto-generated method stub
        return productInfoRepository.save(productInfo);
    }

}
