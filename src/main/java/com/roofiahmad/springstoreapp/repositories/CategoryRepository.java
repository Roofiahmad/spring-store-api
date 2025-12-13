package com.roofiahmad.springstoreapp.repositories;

import com.roofiahmad.springstoreapp.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
