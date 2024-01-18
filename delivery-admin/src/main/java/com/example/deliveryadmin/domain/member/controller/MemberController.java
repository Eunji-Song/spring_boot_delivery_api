package com.example.deliveryadmin.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/members")
public class MemberController {
    /**
     * 관리자 신규 사용자 생성
     */
    @GetMapping("/join")
    public void join() {

    }
}
