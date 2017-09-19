package cn.MrZhang.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import cn.MrZhang.dto.OrderDTO;
import cn.MrZhang.model.OrderMaster;

public class OrderMaster2OrderDTO {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasters) {
        // e -> 后面的 是要转换后的list 中的类型

        return orderMasters.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
