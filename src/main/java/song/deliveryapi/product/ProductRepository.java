package song.deliveryapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.lang.model.type.ArrayType;
import javax.sound.sampled.Port;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductRepository {
    private Map<Integer, Product> db = new HashMap<>();


    public List<Product> getList() {
        List<Product> list = new ArrayList<>(db.values());
        return list;
    }

    public Product getDetailInfo(Integer id) {
        return db.get(id);
    }


    public List<Product> save(ArrayList<Product> products) {
        AtomicInteger index = new AtomicInteger(1);
        products.stream().forEach(product -> {
            int currentIndex = index.getAndIncrement();
            db.put(currentIndex, product);
        });


        return new ArrayList<>(db.values());
    }

    public List<Product> update(Integer id, Product product) {
        db.replace(id, product);
        return new ArrayList<>(db.values());
    }

    public List<Product> delete(Integer id) {
        db.remove(id);
        List<Product> list = new ArrayList<>(db.values());
        return list;
    }


}
