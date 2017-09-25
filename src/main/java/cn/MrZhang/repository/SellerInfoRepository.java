package cn.MrZhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.MrZhang.model.SellerInfo;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByOpenid(String openid);

}
