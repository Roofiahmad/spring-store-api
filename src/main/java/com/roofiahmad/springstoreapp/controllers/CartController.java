package com.roofiahmad.springstoreapp.controllers;

import com.roofiahmad.springstoreapp.dtos.AddItemToCartRequest;
import com.roofiahmad.springstoreapp.dtos.CartDto;
import com.roofiahmad.springstoreapp.dtos.CartItemDto;
import com.roofiahmad.springstoreapp.dtos.UpdateCartItemRequest;
import com.roofiahmad.springstoreapp.entities.Cart;
import com.roofiahmad.springstoreapp.entities.CartItem;
import com.roofiahmad.springstoreapp.mappers.CartMapper;
import com.roofiahmad.springstoreapp.repositories.CartRepository;
import com.roofiahmad.springstoreapp.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cart = new Cart();
        cartRepository.save(cart);
        var cartDto = cartMapper.toDto(cart);

        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId, @RequestBody AddItemToCartRequest request) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        var cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() +1);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }
        cartRepository.save(cart);

        return ResponseEntity.ok(cartMapper.toDto(cartItem));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCart(@PathVariable("cartId") UUID cartId, @PathVariable("productId") Long productId,@Valid @RequestBody UpdateCartItemRequest request) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart not found")
            );
        }

        var product = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Product not found in the cart")
            );
        }

        product.setQuantity(request.getQuantity());
        cartRepository.save(cart);
        return ResponseEntity.ok(cartMapper.toDto(product));

    }
}
