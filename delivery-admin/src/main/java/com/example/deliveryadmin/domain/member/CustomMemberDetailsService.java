package com.example.deliveryadmin.domain.member;

import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    // username(email)을 이용하여 회원 정보 가져오기
    public UserDetails loadUserByUsername(final String accountId) {
        return memberRepository.findOneByAccountId(accountId).orElseThrow(() -> new UsernameNotFoundException(accountId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }


    private org.springframework.security.core.userdetails.User createUser(Member member) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(member.getAccountId(),
                member.getPassword(),
                grantedAuthorities);
    }
}