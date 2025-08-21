package com.sanjiv.eshops.service.order;

import com.sanjiv.eshops.enums.OrderStatus;
import com.sanjiv.eshops.exception.ResourceNotFoundException;
import com.sanjiv.eshops.model.Cart;
import com.sanjiv.eshops.model.Order;
import com.sanjiv.eshops.model.OrderItem;
import com.sanjiv.eshops.model.Product;
import com.sanjiv.eshops.repository.OrderRepository;
import com.sanjiv.eshops.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();

//        set the user
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );
        }).toList();

    }

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
