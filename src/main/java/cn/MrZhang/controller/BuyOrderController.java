package cn.MrZhang.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.MrZhang.converter.OrderForm2OrderDTO;
import cn.MrZhang.dto.OrderDTO;
import cn.MrZhang.exception.ServiceException;
import cn.MrZhang.form.OrderForm;
import cn.MrZhang.service.OrderService;
import cn.MrZhang.util.ResultVoUtil;
import cn.MrZhang.vo.ResultVo;
import lombok.extern.log4j.Log4j;

/**
 * 
 * Title:BuyOrderController
 * @Description: TODO 买家操作
 * @author MrZhang
 * @date 2017年9月19日 上午10:39:31 
 * @version V1.0
 */
@RestController
@RequestMapping("/buyer/order")
@Log4j
public class BuyOrderController {

    @Autowired
    private OrderService orderService;

    // 创建订单
    @PostMapping("/create")
    public ResultVo<Map<String, String>> creat(@Valid OrderForm orderForm) {
        // 订单表单校验
        // if (bindingResult.hasErrors()) {
        // log.error(MessageFormat.format("【创建订单】创建订单失败，orderForm={0}", orderForm));
        // throw new ServiceException(bindingResult.getFieldError().getDefaultMessage());
        // }
        // 将表单转换为返回给前台类型
        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error(MessageFormat.format("【创建订单】创建订单失败，orderForm={0}", orderForm));
            throw new ServiceException("购物车不能空");
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("orderId", result.getOrderId());

        return ResultVoUtil.success(resultMap);
    }

    // 订单列表
    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam("openid") String openid,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {

        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单】查询订单列表失败：openid为空");
            throw new ServiceException("openid为空");
        }
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        Pageable pageable = new PageRequest(pageNo, pageSize, sort);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageable);
        return ResultVoUtil.success(orderDTOPage.getContent());
    }

    // 订单详情

    // 取消订单
}
