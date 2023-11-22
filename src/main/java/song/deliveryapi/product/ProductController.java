package song.deliveryapi.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class ProductController {
    ProductService productService;

    // 의존성 주입을 명시적으로 표현
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 가게에 등로된 음식 목록
    @GetMapping("/products")
    public List<Product> getList() {
        return productService.getList();
    }

    // 가게에 등록왼 음식 목록 > 선택한 음식 상세 데이터
    @GetMapping("/products/{id}")
    public ProductDto getDetailInfo(@PathVariable("id") Long id) {
        return productService.getDetailInfo(id);
    }

    @PostMapping("/products")
    public List<ProductDto> save(@RequestBody List<ProductDto> productDtoList) {
        return productService.save(productDtoList);
    }

    @PutMapping("products/{id}")
    public ProductDto update(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        return productService.update(id, productDto);
    }

    @DeleteMapping("products/{id}")
    public List<ProductDto> delete(@PathVariable("id") Long id) {
        return productService.delete(id);
    }
}
