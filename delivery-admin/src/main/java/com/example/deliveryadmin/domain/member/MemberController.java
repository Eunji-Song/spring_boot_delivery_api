package com.example.deliveryadmin.domain.member;

import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.common.response.ResultCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member(어드민 사용자)")
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
    public ApiResult join(@Valid @RequestBody MemberRequestDto.Join joinDto) {
        memberService.join(joinDto);

        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResult login(@Valid @RequestBody MemberRequestDto.Login loginDto) {
        return ApiResponse.success(ResultCode.SUCCESS, memberService.login(loginDto));
    }
}
