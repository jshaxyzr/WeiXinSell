package cn.MrZhang.service.Impl;

import org.apache.commons.math3.util.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import cn.MrZhang.dto.OrderDTO;
import cn.MrZhang.exception.ServiceException;
import cn.MrZhang.service.OrderService;
import cn.MrZhang.service.PayService;
import cn.MrZhang.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Title:PayServiceImpl
 * @Description: TODO 支付实现
 * @author MrZhang
 * @date 2017年9月21日 下午3:04:45 
 * @version V1.0
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付, request={}", JsonUtils.objectToJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付, response={}", JsonUtils.objectToJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        // 1. 验证签名
        // 2. 支付的状态
        // 3. 支付金额
        // 4. 支付人(下单人 == 支付人)

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知, payResponse={}", JsonUtils.objectToJson(payResponse));

        // 查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        // 判断订单是否存在
        if (orderDTO == null) {
            log.error("【微信支付】异步通知, 订单不存在, orderId={}", payResponse.getOrderId());
            throw new ServiceException("订单不存在");
        }

        // 判断金额是否一致(0.10 0.1)
        if (!MathUtils.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())) {
            log.error("【微信支付】异步通知, 订单金额不一致, orderId={}, 微信通知金额={}, 系统金额={}", payResponse.getOrderId(),
                    payResponse.getOrderAmount(), orderDTO.getOrderAmount());
            throw new ServiceException("订单金额不一致");
        }

        // 修改订单的支付状态
        orderService.paid(orderDTO);

        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}", JsonUtils.objectToJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", JsonUtils.objectToJson(refundResponse));

        return refundResponse;
    }
}
