package song.deliveryapi.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductMapRepository {
    private Map<Integer, Product> db = new HashMap<>();

    public List<Product> getList() {
        List<Product> list = new ArrayList<>(db.values());
        return list;
    }

    public Product getDetailInfo(Integer id) {
        return db.get(id);
    }


    public List<Product> save(List<Product> entityList) {
        AtomicInteger index = new AtomicInteger(1);
        entityList.stream().forEach(product -> {
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
