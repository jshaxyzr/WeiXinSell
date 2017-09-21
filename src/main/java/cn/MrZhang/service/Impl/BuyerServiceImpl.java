package cn.MrZhang.service.Impl;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.MrZhang.dto.OrderDTO;
import cn.MrZhang.exception.ServiceException;
import cn.MrZhang.service.BuyerService;
import cn.MrZhang.service.OrderService;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        // TODO Auto-generated method stub

        return checkOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        // TODO Auto-generated method stub

        OrderDTO orderDTO = checkOwner(openid, orderId);
        if (orderDTO == null) {
            log.error(MessageFormat.format("【取消订单】取消订单失败：查不到orderId={0}的订单", orderId));
            throw new ServiceException("orderId不存在");
        }
        return orderService.cancel(orderDTO);
    }

    @Override
    public OrderDTO findOrderList(String openid) {
        // TODO Auto-generated method stub
        return null;
    }

    private OrderDTO checkOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        // 判断该订单是否为当前用户的订单
        if (!openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())) {
            log.error(MessageFormat.format("【查询订单】查询订单失败：用户openid不一致,openid={0},orderDTO={1}", openid, orderDTO));
            throw new ServiceException("openid不合法");
        }
        return orderDTO;
    }

}
