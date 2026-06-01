package com.roofiahmad.springstoreapp.admin;

import com.roofiahmad.springstoreapp.orders.Order;
import com.roofiahmad.springstoreapp.products.Product;
import com.roofiahmad.springstoreapp.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminDto toDto(User user);
    User toEntity(RegisterAdminRequest request);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    AdminProductDto toAdminProductDto(Product product);
    AdminOrderDto toAdminOrderDto(Order order);
}
