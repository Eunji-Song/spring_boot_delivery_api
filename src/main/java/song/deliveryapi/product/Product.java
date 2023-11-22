package song.deliveryapi.product;

import jakarta.persistence.*;
import lombok.*;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;

import java.math.BigDecimal;

@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor
@ToString
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT를 사용
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "cateogry_id")
    private Integer categoryId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;


    // 기본 생성자
    public Product(Long id, String name, String description, Integer storeId, Integer categoryId, BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Integer storeId() {
        return storeId;
    }

    public Integer categoryId() {
        return categoryId;
    }

    public BigDecimal price() {
        return price;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

}
