package com.example.deliveryuser.domain.member;

import com.example.deliveryuser.common.exception.member.MemberConflictException;
import com.example.deliveryuser.common.exception.member.MemberNotFoundException;
import com.example.deliveryuser.common.exception.member.MemberStatusValidationException;
import com.example.deliveryuser.common.util.SecurityUtil;
import com.example.deliverycore.entity.Member;
import com.example.deliveryuser.common.exception.member.MemberConflictException;
import com.example.deliveryuser.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        log.info("[Member:join] 회원가입 요청 발생 : {}", requestJoinDto.toString());

        // 계정 ID 중복 검사
        isExistAccount(requestJoinDto.getAccountId());

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(requestJoinDto.getPassword());
        requestJoinDto.setPassword(encodePassword);

        // DTO -> Entity
        Member Member = memberMapper.joinDtoToMember(requestJoinDto);
        memberRepository.save(Member);

        return Member.getId();
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

        // 사용자 Id 가져오기
        Long memberId = SecurityUtil.getCurrentMemberId();

        // 유효한 사용자인지 확인
        Member Member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        if (Member.isWithdrawal()) {
            throw new MemberStatusValidationException("이미 탈퇴한 회원입니다.");
        }

        // 상태 업데이트
        Member.setIsWithdrawal(true);
        Member.setWithdrawalDate(LocalDateTime.now());
    }


}
