package com.example.deliveryadmin.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslConfiguration {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory queryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
