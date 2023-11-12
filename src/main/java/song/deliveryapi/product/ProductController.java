package song.deliveryapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;



    // 가게에 등로된 음식 목록
    @GetMapping("/products")
    public List<Product> getList() {
        return productService.getList();
    }

    // 가게에 등록왼 음식 목록 > 선택한 음식 상세 데이터
    @GetMapping("/products/{id}")
    public Product getDetailInfo(@PathVariable("id") Integer id) {
        return productService.getDetailInfo(id);
    }

    @PostMapping("/products")
    public List<Product> save(@RequestBody ArrayList<Product> products) {
        return productService.save(products);
    }

    @PutMapping("products/{id}")
    public List<Product> update(@PathVariable("id") Integer id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping("products/{id}")
    public List<Product> delete(@PathVariable("id") Integer id) {
        return productService.delete(id);
    }
}
