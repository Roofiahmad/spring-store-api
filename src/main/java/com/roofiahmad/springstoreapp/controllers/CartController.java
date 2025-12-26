package com.roofiahmad.springstoreapp.controllers;

import com.roofiahmad.springstoreapp.dtos.AddItemToCartRequest;
import com.roofiahmad.springstoreapp.dtos.CartDto;
import com.roofiahmad.springstoreapp.dtos.CartItemDto;
import com.roofiahmad.springstoreapp.dtos.UpdateCartItemRequest;
import com.roofiahmad.springstoreapp.exceptions.CartNotFoundException;
import com.roofiahmad.springstoreapp.exceptions.ProductNotFoundException;
import com.roofiahmad.springstoreapp.mappers.CartMapper;
import com.roofiahmad.springstoreapp.repositories.CartRepository;
import com.roofiahmad.springstoreapp.repositories.ProductRepository;
import com.roofiahmad.springstoreapp.services.CartService;
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
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.create();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId, @RequestBody AddItemToCartRequest request) {

        var cartItemDto = cartService.addToCart(cartId, request.getProductId());
        return ResponseEntity.ok(cartItemDto);
    }


    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateCart(@PathVariable("cartId") UUID cartId, @PathVariable("productId") Long productId,@Valid @RequestBody UpdateCartItemRequest request) {
        var cartItemDto = cartService.updateCart(cartId, productId, request.getQuantity());
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId) {

        cartService.deleteProduct(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(
            @PathVariable("cartId") UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found in the cart"));
    }
}
