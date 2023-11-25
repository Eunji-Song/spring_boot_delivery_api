package song.deliveryapi.user;

import jakarta.persistence.*;
import lombok.*;
import org.joda.time.LocalDateTime;

@Data // @toString, @getter, @setter, @RequiredArgsConstructor, @EqualsAndHashCode
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 매개변수를 입력받지 않는 기본 생성자를 자동으로 생성
@AllArgsConstructor // 매개변수를 받는 생성자를 자동으로 생성
@Builder // 의미있는 객체를 생성하기 위해 builer 어노테이션 선언
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    private GradeType gradeType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // grade enum
    public enum GradeType {
        BASIC(1),
        SILVER(2),
        GOLD(3),
        VIP(4);

        private final Integer value;

        GradeType(Integer value) {
            this.value = value;
        }
    }
}





