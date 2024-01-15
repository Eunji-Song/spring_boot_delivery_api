package song.deliveryapi.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // JPA에서 fetch 조인을 어노테이션으로 사용
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findByEmail(String email);
}
