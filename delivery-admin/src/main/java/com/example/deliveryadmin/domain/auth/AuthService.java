package com.example.deliveryadmin.domain.auth;

import com.example.deliveryadmin.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    // UsernameNotFoundException
    public AuthResponseDto.CheckInfo getUserInfo(String accountId) {
        Member member = authRepository.findOneByAccountId(accountId);

        AuthResponseDto.CheckInfo info = authMapper.memberEntityToCheckDto(member);
        return info;
    }
}
