package com.sanjiv.eshops.service.cart;

import com.sanjiv.eshops.exception.ResourceNotFoundException;
import com.sanjiv.eshops.model.Cart;
import com.sanjiv.eshops.model.CartItem;
import com.sanjiv.eshops.repository.CartItemRepository;
import com.sanjiv.eshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);

        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
//        return cart.getCartItems()
//                .stream()
//                .map(CartItem :: getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return cart.getTotalAmount();
    }
}
