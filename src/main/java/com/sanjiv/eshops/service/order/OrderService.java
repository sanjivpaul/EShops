package com.sanjiv.eshops.service.order;

import com.sanjiv.eshops.dto.OrderDto;
import com.sanjiv.eshops.enums.OrderStatus;
import com.sanjiv.eshops.exception.ResourceNotFoundException;
import com.sanjiv.eshops.model.Cart;
import com.sanjiv.eshops.model.Order;
import com.sanjiv.eshops.model.OrderItem;
import com.sanjiv.eshops.model.Product;
import com.sanjiv.eshops.repository.OrderRepository;
import com.sanjiv.eshops.repository.ProductRepository;
import com.sanjiv.eshops.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

//    So @Transactional basically tells Spring:
//            “Please manage a transaction for this method, so my database changes happen properly.”
    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId); // fetch cart by user id

//        TODO: check cart if not cart by user then return a message
//        TODO: If Card length is 0 then return me message select item to place orders

        Order order = createOrder(cart); // create order of that cart
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList)); // set orders
        order.setTotalAmount(calculateTotalPrice(orderItemList)); // set total amounts
        Order savedOrder = orderRepository.save(order); // save order

//        after order saved clear the cart
        cartService.clearCart(cart.getId());

        return savedOrder; // return saved order
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();

//        set the user
        order.setUser(cart.getUser());

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
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()-> new ResourceNotFoundException("Order Not Found!"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    @Override // convert to interface level
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
