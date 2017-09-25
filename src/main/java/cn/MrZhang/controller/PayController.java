package cn.MrZhang.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lly835.bestpay.model.PayResponse;

import cn.MrZhang.dto.OrderDTO;
import cn.MrZhang.exception.ServiceException;
import cn.MrZhang.service.OrderService;
import cn.MrZhang.service.PayService;

/**
 * 
 * Title:PayController
 * @Description: TODO 支付
 * @author MrZhang
 * @date 2017年9月21日 下午3:08:35 
 * @version V1.0
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public String create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl,
            Map<String, Object> map, Model model) {
        // 1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new ServiceException("订单不存在");
        }

        // 2. 发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        model.addAttribute(map);
        return "/pay/create";
    }

    /**
     * 微信异步通知
     * @param notifyData
     */
    @PostMapping("/notify")
    public String notify(@RequestBody String notifyData) {
        payService.notify(notifyData);

        // 返回给微信处理结果
        return "/pay/success";
    }
}
