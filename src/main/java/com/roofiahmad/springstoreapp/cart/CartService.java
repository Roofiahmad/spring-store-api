package com.roofiahmad.springstoreapp.cart;

import com.roofiahmad.springstoreapp.cart.dto.CartDto;
import com.roofiahmad.springstoreapp.cart.dto.CartItemDto;
import com.roofiahmad.springstoreapp.order.exception.CartNotFoundException;
import com.roofiahmad.springstoreapp.product.ProductNotFoundException;
import com.roofiahmad.springstoreapp.product.ProductRepository;
import com.roofiahmad.springstoreapp.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Value("${order.shippingFee}")
    private BigDecimal defaultShippingFee;

    @Value("${order.valueAddedTax}")
    private BigDecimal vatRate;

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

    public CartDto getCart() {
        var user = Utils.getUserPrincipal();
        var cart = cartRepository.getCartWithUserId(user.getId())
                .orElseThrow(CartNotFoundException::new);

        CartDto cartDto = cartMapper.toDto(cart);

        BigDecimal subTotal = cart.getSubTotal();
        BigDecimal calculatedVat = subTotal.multiply(vatRate);
        BigDecimal finalTotalPrice = subTotal.add(defaultShippingFee).add(calculatedVat);

        cartDto.setSubTotal(subTotal);
        cartDto.setShippingFee(defaultShippingFee);
        cartDto.setVatAmount(calculatedVat);
        cartDto.setTotalPrice(finalTotalPrice);

        return cartDto;
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
