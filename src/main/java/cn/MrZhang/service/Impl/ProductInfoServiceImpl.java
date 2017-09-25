package cn.MrZhang.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.MrZhang.dto.CartDTO;
import cn.MrZhang.enums.ProductStatusEnum;
import cn.MrZhang.exception.ServiceException;
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
    @Transactional
    public ProductInfo save(ProductInfo productInfo) {
        // TODO Auto-generated method stub
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    // 加库存
    public void increaseStock(List<CartDTO> cartDTOs) {
        // TODO Auto-generated method stub
        for (CartDTO cartDTO : cartDTOs) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new ServiceException("商品不存在");
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    // 扣库存
    public void decreaseStock(List<CartDTO> cartDTOs) {
        // TODO Auto-generated method stub

        for (CartDTO cartDTO : cartDTOs) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new ServiceException("商品不存在");
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new ServiceException("库存不足！");
            }
            // 设置结果为新库存
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);

        }

    }

    @Override
    @Transactional
    public ProductInfo onSale(String productId) {
        // TODO Auto-generated method stub
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new ServiceException("商品不存在");
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new ServiceException("商品已经是上架状态!");
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        ProductInfo result = productInfoRepository.save(productInfo);
        return result;
    }

    @Override
    public ProductInfo offSale(String productId) {
        // TODO Auto-generated method stub
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new ServiceException("商品不存在");
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.Down) {
            throw new ServiceException("商品已经是上架状态!");
        }
        productInfo.setProductStatus(ProductStatusEnum.Down.getCode());
        ProductInfo result = productInfoRepository.save(productInfo);
        return result;
    }

}
