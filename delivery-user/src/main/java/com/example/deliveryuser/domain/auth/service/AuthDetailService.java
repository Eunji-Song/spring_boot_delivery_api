package com.example.deliveryuser.domain.auth.service;

import com.example.deliverycore.entity.Member;
import com.example.deliveryuser.domain.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthDetailService implements UserDetailsService {
    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        log.info("[AuthDetailService:loadUserByUsername] accountId : {}", accountId);

        Member member = authRepository.findOneByAccountId(accountId);
        if (member == null) {
            throw new UsernameNotFoundException(accountId + " 계정 사용자가 존재하지 않습니다.");
        }

        return member;
    }
}
