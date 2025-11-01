package com.sanjiv.eshops.service.order;

import com.sanjiv.eshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);

    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
