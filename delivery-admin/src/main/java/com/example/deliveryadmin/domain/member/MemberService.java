package com.example.deliveryadmin.domain.member;

import com.example.deliveryadmin.common.exception.member.MemberConflictException;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
