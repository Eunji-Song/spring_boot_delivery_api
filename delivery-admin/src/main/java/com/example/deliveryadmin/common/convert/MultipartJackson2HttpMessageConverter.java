package com.example.deliveryadmin.common.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * "Content-Type: multipart/form-data" 헤더를 지원하는 HTTP 요청 변환기
 * swagger Content-Type 'application/octet-stream' is not supported. 에러 해결을 위한 converter 생성
 * return false: 컨버터가 특정 클래스 또는 미디어 타입에 대한 쓰기 지원을 하지 않도록 설정
 * 요청에만 변환되어야 하고, 응답에는 사용되어야 하지 않기 때문에 false로 설정
 */
@Component
public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }
}
