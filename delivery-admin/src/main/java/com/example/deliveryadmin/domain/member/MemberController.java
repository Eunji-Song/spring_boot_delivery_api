package com.example.deliveryadmin.domain.member;

import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
