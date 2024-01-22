package com.example.deliveryadmin.common.util;

import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.common.response.ResultCode;
import com.example.deliveryadmin.domain.member.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

   private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

   private SecurityUtil() {}

   /**
    * 토큰을 이용한 사용자의 id값 리턴
    */
   public static Long getCurrentMemberId() {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null) {
         throw new RuntimeException(" Security Context에 인증 정보가 없습니다.");
      }

      Long adminId = null;
      if (authentication.getPrincipal() instanceof UserDetails) {
         Member springSecurityMember = (Member) authentication.getPrincipal();
         adminId = springSecurityMember.getId();
      }

      if (adminId == null) {
         throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
      }

      return adminId;
   }

}
