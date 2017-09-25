package cn.MrZhang.service;

import cn.MrZhang.model.SellerInfo;

public interface SellerInfoService {
    /**
     * 
    * @Title: findSellerInfoByOpenid
    * @Description: TODO 根据微信openid 查询
    * @param openid
    * @return SellerInfo
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
