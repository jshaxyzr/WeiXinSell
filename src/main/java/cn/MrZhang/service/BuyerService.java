package cn.MrZhang.service;

import cn.MrZhang.dto.OrderDTO;

public interface BuyerService {

    /** 查询一个订单 */

    OrderDTO findOrderOne(String openid, String orderId);

    /** 取消订单*/

    OrderDTO cancelOrder(String openid, String orderId);

    /** 查询订单列表 */

    OrderDTO findOrderList(String openid);
}
