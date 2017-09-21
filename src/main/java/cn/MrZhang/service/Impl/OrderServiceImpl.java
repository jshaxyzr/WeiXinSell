package cn.MrZhang.service.Impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.MrZhang.converter.OrderMaster2OrderDTO;
import cn.MrZhang.dto.CartDTO;
import cn.MrZhang.dto.OrderDTO;
import cn.MrZhang.enums.OrderStatusEnum;
import cn.MrZhang.enums.PayStatusEnum;
import cn.MrZhang.exception.ServiceException;
import cn.MrZhang.model.OrderDetail;
import cn.MrZhang.model.OrderMaster;
import cn.MrZhang.model.ProductInfo;
import cn.MrZhang.repository.OrderDetailRepository;
import cn.MrZhang.repository.OrderMasterRepository;
import cn.MrZhang.service.OrderDetailService;
import cn.MrZhang.service.OrderService;
import cn.MrZhang.service.ProductInfoService;
import cn.MrZhang.util.IDUtils;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        // 生成订单id
        String orderId = IDUtils.genId();
        // 订单总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        // 1.查询商品
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            // 根据订单详情表中的 商品id 查询商品信息
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new ServiceException("商品不存在");
            }
            // 2、 计算订单总价 BigDecimal 类型乘法 不能直接用 * 要用 multiply
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            // 3、订单入库
            orderDetail.setDetailId(IDUtils.genId());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailService.save(orderDetail);
        }
        // 4、写入订单数据库 要先拷贝属性 在设置独有的属性值 ，要不然 属性为null的值也会拷贝过去 并覆盖自己也set过的值
        // 上面的 拷贝因为 productInfo 的属性 中并无detailId orderId 不会覆盖设置值
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        // orderMaster new 出来的时候已经对orderStatus payStatus 进行了初始化复制 拷贝后 会覆盖初始化值因此下面要重新
        // 初始化一次
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        // 5、扣库存 lambda
        List<CartDTO> cartDTOs = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOs);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        // TODO Auto-generated method stub

        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new ServiceException("订单不存在");
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new ServiceException("订单购物车为空");
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        // TODO Auto-generated method stub
        // 根据微信id获取订单分页信息
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        // 将后台订单分页信息转换为返回给前台的订单信息类型
        List<OrderDTO> orderDTOs = OrderMaster2OrderDTO.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOs, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        // 1、判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            // orderId={},orderStatus={} 占位符方式 传统的是进行string 拼接"orderId="+orderId+"orderStatus="+...
            log.error(MessageFormat.format("【取消订单】订单状态不正确，orderId={0},orderStatus={1}", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus()));
            throw new ServiceException("订单状态异常");
        }
        // 2、修改订单状态(数据库中的)
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error(MessageFormat.format("【取消订单】更新订单状态失败，orderMaster={0}", orderMaster));
            throw new ServiceException("更新订单状态失败");
        }
        // 3、修改库存（添加该订单的数量）
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error(MessageFormat.format("【取消订单】返还库存失败，orderDTO={0}", orderDTO));
            throw new ServiceException("订单中无商品");
        }
        List<CartDTO> cartDTOs = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOs);
        // 4、如果用户已支付 则进行退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        // 1 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error(MessageFormat.format("【支付订单】订单状态不正确，orderId={0},orderStatus={1}", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus()));
            throw new ServiceException("订单状态异常");
        }

        // 2 判断支付状态 如果不是等待支付 报错
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error(MessageFormat.format("【支付订单】支付状态不正确，orderId={0},payStatus={1}", orderDTO.getOrderId(),
                    orderDTO.getPayStatus()));
            throw new ServiceException("订单支付状态异常");
        }
        // 3 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error(MessageFormat.format("【支付订单】更新订单支付状态失败，orderMaster={0}", orderMaster));
            throw new ServiceException("完结订单状态失败");
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        // 1 判断订单状态 如果不是新订单（以前的订单 已完成的订单等 报错）
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error(MessageFormat.format("【完成订单】订单状态不正确，orderId={0},orderStatus={1}", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus()));
            throw new ServiceException("订单状态异常");
        }
        // 2 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINSHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error(MessageFormat.format("【完成订单】更新订单状态失败，orderMaster={0}", orderMaster));
            throw new ServiceException("完结订单状态失败");
        }
        return orderDTO;
    }

}
