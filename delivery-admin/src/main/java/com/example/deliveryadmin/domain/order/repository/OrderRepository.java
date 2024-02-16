package com.example.deliveryadmin.domain.order.repository;

import com.example.deliverycore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

}
