package com.roofiahmad.springstoreapp.mappers;

import com.roofiahmad.springstoreapp.dtos.CartDto;
import com.roofiahmad.springstoreapp.dtos.CartItemDto;
import com.roofiahmad.springstoreapp.entities.Cart;
import com.roofiahmad.springstoreapp.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target="totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target="totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);

}
