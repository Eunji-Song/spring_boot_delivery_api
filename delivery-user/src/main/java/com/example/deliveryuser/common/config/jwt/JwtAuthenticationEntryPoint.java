package com.example.deliveryuser.common.config.jwt;

import com.example.deliveryuser.common.response.ApiResult;
import com.example.deliveryuser.common.response.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json;charset=UTF-8");

      ApiResult result = new ApiResult(ResultCode.UNAUTHORIZED_USER);

      ObjectMapper mapper = new ObjectMapper(); // 객체 -> JSON 문자열로 직렬화
      String msg = mapper.writeValueAsString(result);

      response.getWriter().write(msg);
   }


}
