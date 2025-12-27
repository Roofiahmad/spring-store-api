package com.roofiahmad.springstoreapp.services;

import com.roofiahmad.springstoreapp.dtos.CheckoutRequest;
import com.roofiahmad.springstoreapp.dtos.CheckoutResponse;
import com.roofiahmad.springstoreapp.entities.Order;
import com.roofiahmad.springstoreapp.exceptions.CartEmptyException;
import com.roofiahmad.springstoreapp.exceptions.CartNotFoundException;
import com.roofiahmad.springstoreapp.repositories.CartRepository;
import com.roofiahmad.springstoreapp.repositories.OrderRepository;
import com.roofiahmad.springstoreapp.repositories.UserRepository;
import com.roofiahmad.springstoreapp.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CheckoutService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;

    public CheckoutResponse checkout(CheckoutRequest request, UserPrincipal principal) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if(cart.isEmpty()) {
           throw new CartEmptyException();
        }

        var customer = userRepository.findById(principal.getId()).orElseThrow();
        var order = Order.fromCart(cart, customer);

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
