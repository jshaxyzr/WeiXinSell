package cn.MrZhang.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.MrZhang.model.SellerInfo;
import cn.MrZhang.repository.SellerInfoRepository;
import cn.MrZhang.service.SellerInfoService;

@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(openid);
        return sellerInfo;
    }

}
