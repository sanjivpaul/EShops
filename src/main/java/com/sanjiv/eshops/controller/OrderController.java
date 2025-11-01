package com.sanjiv.eshops.controller;

import com.sanjiv.eshops.model.Order;
import com.sanjiv.eshops.response.ApiResponse;
import com.sanjiv.eshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/v1/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error Occurred!", e.getMessage()));
        }
    }

    @GetMapping("/v1/{orderId/orders}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            Order order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order fetched", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error fetch product", e.getMessage()));
        }
    }
}
