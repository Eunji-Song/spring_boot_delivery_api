package com.example.deliveryadmin.domain.category.repository;

import com.example.deliverycore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
