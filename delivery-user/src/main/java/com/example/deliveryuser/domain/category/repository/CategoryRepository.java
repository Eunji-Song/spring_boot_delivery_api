package com.example.deliveryuser.domain.category.repository;

import com.example.deliverycore.entity.Category;
import com.example.deliveryuser.domain.category.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
}
