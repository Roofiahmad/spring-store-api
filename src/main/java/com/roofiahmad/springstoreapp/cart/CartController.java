package com.roofiahmad.springstoreapp.cart;

import com.roofiahmad.springstoreapp.cart.dto.AddItemToCartRequest;
import com.roofiahmad.springstoreapp.cart.dto.CartDto;
import com.roofiahmad.springstoreapp.cart.dto.CartItemDto;
import com.roofiahmad.springstoreapp.cart.dto.UpdateCartItemRequest;
import com.roofiahmad.springstoreapp.common.dto.ErrorDto;
import com.roofiahmad.springstoreapp.order.exception.CartNotFoundException;
import com.roofiahmad.springstoreapp.product.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.create();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Add a product to the cart. ")
    public ResponseEntity<CartItemDto> addToCart(
            @Parameter(description = "The ID of the cart.")
            @PathVariable UUID cartId, @RequestBody AddItemToCartRequest request) {
       var cartItemDto = cartService.addToCart(cartId, request.getProductId());
       return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }


    @GetMapping()
    public CartDto getCart() {
        return cartService.getCart();
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCart(@PathVariable("cartId") UUID cartId, @PathVariable("productId") Long productId, @Valid @RequestBody UpdateCartItemRequest request) {
        return cartService.updateCart(cartId, productId, request.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId) {

        cartService.deleteProduct(cartId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(
            @PathVariable("cartId") UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Product not found in the cart"));
    }
}
