package com.example.deliverycore.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 사용자 계정
 */

@Entity
@Table(name = "member")
@Getter
public class Member extends BaseEntity implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "name")
    private String name;

    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "is_withdrawal", nullable = false)
    @ColumnDefault("false")
    private boolean isWithdrawal;

    // 탈퇴일자
    @Column(updatable = false)
    private LocalDateTime withdrawalDate;

    // 오버라이드 된 메소드
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) : 역직렬화 할 때만 사용 , json -> object
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.accountId;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }


    public Member() {
    }

    @Builder(toBuilder = true)
    public Member(Long id, String accountId, String name, String password, String email, boolean isWithdrawal, LocalDateTime withdrawalDate) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.isWithdrawal = isWithdrawal;
        this.withdrawalDate = withdrawalDate;
    }

    public Member(Long id) {
        this.id = id;
    }

    // setter

    public void setIsWithdrawal(boolean withdrawal) {
        isWithdrawal = withdrawal;
    }

    public void setWithdrawalDate(LocalDateTime withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }
}
