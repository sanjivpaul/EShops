package com.sanjiv.eshops.service.cart;

import com.sanjiv.eshops.model.Cart;
import com.sanjiv.eshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
