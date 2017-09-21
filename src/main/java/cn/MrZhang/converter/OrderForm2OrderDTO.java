package cn.MrZhang.converter;

import cn.MrZhang.dto.OrderDTO;
import cn.MrZhang.form.OrderForm;
import cn.MrZhang.model.OrderDetail;
import cn.MrZhang.util.JsonUtils;

public class OrderForm2OrderDTO {

    public static OrderDTO convert(OrderForm orderForm) {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setOrderDetailList(JsonUtils.jsonToList(orderForm.getItems(), OrderDetail.class));
        return orderDTO;
    }
}
