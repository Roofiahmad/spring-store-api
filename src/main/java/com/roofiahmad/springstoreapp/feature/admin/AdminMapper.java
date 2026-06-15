package com.roofiahmad.springstoreapp.feature.admin;

import com.roofiahmad.springstoreapp.feature.admin.dto.AdminDto;
import com.roofiahmad.springstoreapp.feature.admin.dto.AdminOrderDto;
import com.roofiahmad.springstoreapp.feature.admin.dto.AdminProductDto;
import com.roofiahmad.springstoreapp.feature.admin.dto.RegisterAdminRequest;
import com.roofiahmad.springstoreapp.feature.order.Order;
import com.roofiahmad.springstoreapp.feature.product.Product;
import com.roofiahmad.springstoreapp.feature.user.entity.User;
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
