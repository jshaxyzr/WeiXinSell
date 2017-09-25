package cn.MrZhang.service;

import cn.MrZhang.dto.OrderDTO;

/**
 * 
 * Title:PushMessageService
 * @Description: TODO  推送消息
 * @author MrZhang
 * @date 2017年9月25日 下午4:52:25 
 * @version V1.0
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
