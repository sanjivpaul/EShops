package com.sanjiv.eshops.service.cart;

import com.sanjiv.eshops.repository.CartItemRepository;
import com.sanjiv.eshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
//        1. Get the cart
//        2. Get the product
//        3. Check if the product already in the cart
//        4. If yes, then increase the quantity with the requested quantity
//        5. If no, then initiate a new CartItem entry.

    }

    @Override
    public void removedItemFromCart(Long cartId, Long productId) {

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

    }
}
