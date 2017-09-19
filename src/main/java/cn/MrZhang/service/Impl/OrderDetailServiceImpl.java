package cn.MrZhang.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.MrZhang.model.OrderDetail;
import cn.MrZhang.repository.OrderDetailRepository;
import cn.MrZhang.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        // TODO Auto-generated method stub
        return orderDetailRepository.save(orderDetail);
    }
}
