package com.roofiahmad.springstoreapp.repositories;

import com.roofiahmad.springstoreapp.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
