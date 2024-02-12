package com.example.deliveryadmin.domain.admin;

import com.example.deliveryadmin.common.exception.member.MemberConflictException;
import com.example.deliveryadmin.common.exception.member.MemberNotFoundException;
import com.example.deliveryadmin.common.exception.member.MemberStatusValidationException;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.admin.repository.AdminRepository;
import com.example.deliverycore.entity.Admin;
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
public class AdminService {
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(AdminDto.RequestJoinDto requestJoinDto) {
        log.info("[Admin:join] 회원가입 요청 발생 : {}", requestJoinDto.toString());

        // 계정 ID 중복 검사
        isExistAccount(requestJoinDto.getAccountId());

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(requestJoinDto.getPassword());
        requestJoinDto.setPassword(encodePassword);

        // DTO -> Entity
        Admin admin = adminMapper.joinDtoToAdmin(requestJoinDto);
        adminRepository.save(admin);

        return admin.getId();
    }

    /**
     * 회원가입 - ID 중복 검사
     */
    private void isExistAccount(String accountId) {
        log.info("[Admin:isExistAccount] 데이터 중복 조회 : {}", accountId);
        boolean isDuplicatedId = adminRepository.existsByAccountId(accountId);
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
        log.info("[Admin:withdrawal] 회원 탈퇴 요청");

        // 사용자 Id 가져오기
        Long memberId = SecurityUtil.getCurrentAdminId();

        // 유효한 사용자인지 확인
        Admin admin = adminRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        if (admin.isWithdrawal()) {
            throw new MemberStatusValidationException("이미 탈퇴한 회원입니다.");
        }

        // 상태 업데이트
        admin.setIsWithdrawal(true);
        admin.setWithdrawalDate(LocalDateTime.now());
    }


}
