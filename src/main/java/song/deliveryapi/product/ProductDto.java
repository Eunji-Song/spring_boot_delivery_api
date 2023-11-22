package song.deliveryapi.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;


@Setter
@Getter
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Integer storeId;
    private Integer categoryId;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductDto(Long id, String name, String description, Integer storeId, Integer categoryId, BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public ProductDto() {

    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
