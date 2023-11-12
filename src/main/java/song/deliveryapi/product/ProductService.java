package song.deliveryapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getList() {
        return productRepository.getList();
    }

    public Product getDetailInfo(Integer id) {
        return productRepository.getDetailInfo(id);
    }

    public List<Product> save(ArrayList<Product> products) {
        return productRepository.save(products);
    }

    public List<Product> update(Integer id, Product product) {
        return productRepository.update(id, product);
    }

    public List<Product> delete(Integer id) {
        return productRepository.delete(id);
    }
}
