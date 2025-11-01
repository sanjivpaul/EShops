package com.sanjiv.eshops.service.order;

import com.sanjiv.eshops.dto.OrderDto;
import com.sanjiv.eshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
