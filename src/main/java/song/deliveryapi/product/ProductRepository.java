package song.deliveryapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.lang.model.type.ArrayType;
import javax.sound.sampled.Port;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
