package com.example.deliveryadmin.domain.member;

import com.example.deliveryadmin.common.config.jwt.TokenProvider;
import com.example.deliveryadmin.common.exception.member.MemberConflictException;
import com.example.deliveryadmin.common.response.ResultCode;
import com.example.deliveryadmin.domain.member.MemberRequestDto;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.MemberMapper;
import com.example.deliveryadmin.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public Long join(MemberRequestDto.Join joinDto) {
        log.info("회원가입 요청 : {}", joinDto);
        // 계정 ID 중복 검사
        boolean isDuplicatedId = isDuplicatedMemberId(joinDto.getAccountId());
        if (isDuplicatedId) {
            throw new MemberConflictException(ResultCode.DATA_DUPLICATION_USER);
        }

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(joinDto.getPassword());
        joinDto.setPassword(encodePassword);

        // DTO -> Entity
        Member member = memberMapper.joinDtoToEntity(joinDto);
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 계정 ID 중복 검사
     */
    private boolean isDuplicatedMemberId(String accountId) {
        Optional<Member> member = memberRepository.findOneByAccountId(accountId);
        if (member.isPresent()) {
            return true;
        }
        return false;
    }

    /**
     * 로그인
     */
    @Transactional
    public MemberResponseDto.Login login(MemberRequestDto.Login loginDto) {
        // 입력한 값을 기준으로 인증 후 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getAccountId(), loginDto.getPassword());

        // 인증 수행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String accessToken = tokenProvider.createAccessToken(authentication);
        log.info("accessToken : {} ", accessToken );

        String refreshToken = tokenProvider.createRefreshToken(authentication);
        log.info("refreshToken : {}", refreshToken);

        // Token data DB에 저장


        // DTO에 데이터 추가 후 리턴
        MemberResponseDto.Login login = MemberResponseDto.Login.builder().accessToken(accessToken).refreshToken(refreshToken).build();

        return login;
    }
}
