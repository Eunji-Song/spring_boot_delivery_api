package com.example.deliveryadmin.domain.member.service;

import com.example.deliveryadmin.common.config.jwt.TokenProvider;
import com.example.deliveryadmin.common.exception.member.MemberConflictException;
import com.example.deliveryadmin.common.exception.member.MemberNotFoundException;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.MemberMapper;
import com.example.deliveryadmin.domain.member.MemberDto;
import com.example.deliveryadmin.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(MemberDto.RequestJoinDto requestJoinDto) {
        log.info("[Member:join] 회원가입 요청 발생 : {}", requestJoinDto);

        // 계정 ID 중복 검사
        isExistAccount(requestJoinDto.getAccountId());

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(requestJoinDto.getPassword());
        requestJoinDto.setPassword(encodePassword);

        // DTO -> Entity
        Member member = memberMapper.joinDtoToMember(requestJoinDto);
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 회원가입 - ID 중복 검사
     */
    private void isExistAccount(String accountId) {
        log.info("[Member:isExistAccount] 데이터 중복 조회 : {}", accountId);
        boolean isDuplicatedId = memberRepository.existsByAccountId(accountId);
        if (isDuplicatedId) {
            log.info("www");
            throw new MemberConflictException(accountId);
        }
    }


    /**
     * 로그인
     */
    @Transactional
    public MemberDto.Login login(MemberDto.RequestLoginDto requestLoginDto) {
        log.info("[Member:join] 로그인 요청 발생 : {}", requestLoginDto);
        // 입력한 값을 기준으로 인증 후 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestLoginDto.getAccountId(), requestLoginDto.getPassword());

        // 인증 수행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String accessToken = tokenProvider.createAccessToken(authentication);
        log.info("accessToken : {} ", accessToken );

        String refreshToken = tokenProvider.createRefreshToken(authentication);
        log.info("refreshToken : {}", refreshToken);



        // DTO에 데이터 추가 후 리턴
        MemberDto.Login login = MemberDto.Login
                                    .builder()
                                    .accessToken(accessToken)
                                    .refreshToken(refreshToken)
                                    .build();

        return login;
    }

    /**
     * 회원탈퇴
     * Token값으로 처리
     */
    @Transactional
    public void withdrawal() {
        log.info("[Member:withdrawal] 회원 탈퇴 요청");
        Long memberId = SecurityUtil.getCurrentMemberId();
        log.info("member id : {}", memberId);

        // 회원 존재 여부 검사
        memberRepository.withdrawal(memberId);
    }


}
