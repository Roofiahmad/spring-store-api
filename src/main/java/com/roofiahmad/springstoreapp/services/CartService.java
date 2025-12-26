package com.roofiahmad.springstoreapp.services;

import com.roofiahmad.springstoreapp.dtos.CartDto;
import com.roofiahmad.springstoreapp.dtos.CartItemDto;
import com.roofiahmad.springstoreapp.entities.Cart;
import com.roofiahmad.springstoreapp.exceptions.CartNotFoundException;
import com.roofiahmad.springstoreapp.exceptions.ProductNotFoundException;
import com.roofiahmad.springstoreapp.mappers.CartMapper;
import com.roofiahmad.springstoreapp.repositories.CartRepository;
import com.roofiahmad.springstoreapp.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartDto create() {
        var cart = new Cart();
        cartRepository.save(cart);
       return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        var cartItem = cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        };

        return cartMapper.toDto(cart);
    }

    public CartItemDto updateCart(UUID cartId, Long productId, Integer productQuantity) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var cartItem = cart.getCartItem(productId);
        if (cartItem == null) {
            throw new ProductNotFoundException();
        }

        cartItem.setQuantity(productQuantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void deleteProduct(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var product = cart.getCartItem(productId);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        cart.removeItem(product);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
           throw new CartNotFoundException();
        }

        cart.clear();
        cartRepository.save(cart);
    }
}
