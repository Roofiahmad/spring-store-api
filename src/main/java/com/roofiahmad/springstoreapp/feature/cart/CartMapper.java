package com.roofiahmad.springstoreapp.feature.cart;

import com.roofiahmad.springstoreapp.feature.cart.dto.CartDto;
import com.roofiahmad.springstoreapp.feature.cart.dto.CartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target="subTotal", expression = "java(cart.getSubTotal())")
    CartDto toDto(Cart cart);

    @Mapping(source = "product.id", target = "id")
    @Mapping(source = "product", target = ".")
    @Mapping(target="totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);

}
