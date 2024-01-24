package com.example.deliveryadmin.domain.member;

import com.example.deliveryadmin.common.exception.member.MemberNotFoundException;
import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.common.response.ResultCode;
import com.example.deliveryadmin.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Member - 어드민 사용자")
@RestController
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 관리자 신규 사용자 생성
     */
    @PostMapping("/join")
    @Operation(summary = "회원가입")
    public ApiResult join(@Valid @RequestBody MemberDto.RequestJoinDto requestJoinDto) {
        memberService.join(requestJoinDto);

        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResult login(@Valid @RequestBody MemberDto.RequestLoginDto requestLoginDto) {
        MemberDto.Login login = memberService.login(requestLoginDto);
        return ApiResponse.success(ResultCode.SUCCESS, login);
    }


    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/withdrawal")
    public ApiResult withdrawal() {
        log.info("[MemberController:withdrawal]");
        memberService.withdrawal();
        return ApiResponse.success();
    }
}
