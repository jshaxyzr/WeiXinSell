package cn.MrZhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.MrZhang.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
