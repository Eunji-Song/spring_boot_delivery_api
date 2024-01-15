package song.deliveryapi.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import song.deliveryapi.domain.auth.Authority;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor // 필두로 작성된
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}


