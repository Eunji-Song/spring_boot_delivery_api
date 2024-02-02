package com.example.deliveryadmin.common.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

/**
 * 주소 정보
 * 지번 : 시,도 / 군,구 / **동 / N호
 * 도로명 : 서울특별시 / 00구 / **로 ##길 0-00 N호
 * + 우편번호
 */

@Getter
@Embeddable
public class Address {
    // 시, 도
    private String city;

    // 구, 군
    private String district;

    // 동, 읍, 면 & 도로명 + 건물번호
    private String street;

    // 상세 주소 (동,층,호)
    private String detail;

    private String zipCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(district, address.district) && Objects.equals(street, address.street) && Objects.equals(detail, address.detail) && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, district, street, detail, zipCode);
    }

    // validation check
}
