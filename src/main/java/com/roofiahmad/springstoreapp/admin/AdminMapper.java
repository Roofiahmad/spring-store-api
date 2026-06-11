package com.roofiahmad.springstoreapp.admin;

import com.roofiahmad.springstoreapp.admin.dto.AdminDto;
import com.roofiahmad.springstoreapp.admin.dto.AdminOrderDto;
import com.roofiahmad.springstoreapp.admin.dto.AdminProductDto;
import com.roofiahmad.springstoreapp.admin.dto.RegisterAdminRequest;
import com.roofiahmad.springstoreapp.order.Order;
import com.roofiahmad.springstoreapp.product.Product;
import com.roofiahmad.springstoreapp.user.entity.User;
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
