package cn.MrZhang.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.MrZhang.dto.OrderDTO;
import cn.MrZhang.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 
    * @Title: list
    * @Description: TODO 订单列表
    * @param pageNo
    * @param pageSize 第几页, 从1页开始
    * @param model
    * @return String
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize, Model model) {
        PageRequest request = new PageRequest(pageNo - 1, pageSize);

        Page<OrderDTO> orderDTOPage = orderService.findList(request);

        model.addAttribute("orderDTOPage", orderDTOPage);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("size", pageSize);
        return "/order/list.html";
    }

    /**
    * 
    * @Title: cancel
    * @Description: TODO  取消订单
    * @param orderId
    * @param map
    * @return ModelAndView
    */
    @GetMapping("/cancel")
    public String cancel(@RequestParam("orderId") String orderId, Model model) {
        Map<String, Object> map = new HashMap<>();
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (Exception e) {
            log.error("【卖家端取消订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/seller/order/list");
            map.put("OK测试Map传值", orderId);
            model.addAllAttributes(map);
            return "/common/error.html";
        }

        map.put("msg", "订单取消成功");
        map.put("url", "/seller/order/list");
        model.addAllAttributes(map);
        return "/common/success.html";
    }

    /**
     * 
    * @Title: detail
    * @Description: TODO 订单详情
    * @param orderId
    * @param model
    * @return String
     */
    @GetMapping("/detail")
    public String detail(@RequestParam("orderId") String orderId, Model model) {
        OrderDTO orderDTO = new OrderDTO();
        Map<String, Object> map = new HashMap<>();
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (Exception e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/seller/order/list");
            model.addAllAttributes(map);
            return "/common/error.html";
        }

        map.put("orderDTO", orderDTO);
        model.addAllAttributes(map);
        return "/order/detail.html";
    }

    /**
     * 
    * @Title: finished
    * @Description: TODO  完结订单
    * @param orderId
    * @param model
    * @return String
     */
    @GetMapping("/finish")
    public String finished(@RequestParam("orderId") String orderId, Model model) {
        Map<String, Object> map = new HashMap<>();
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (Exception e) {
            log.error("【卖家端完结订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/seller/order/list");
            model.addAllAttributes(map);
            return "/common/error.html";
        }

        map.put("msg", "订单完结成功");
        map.put("url", "/seller/order/list");
        model.addAllAttributes(map);
        return "/common/success.html";
    }
}
