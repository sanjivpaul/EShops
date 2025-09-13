package com.sanjiv.eshops.service.order;

import com.sanjiv.eshops.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);

    Order getOrder(Long orderId);
}
