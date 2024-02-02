package com.example.deliverycore.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpeningHours {
    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "break_start_time")
    private LocalTime breakStartTime;

    @Column(name = "break_end_time")
    private LocalTime breakEndTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpeningHours that = (OpeningHours) o;
        return Objects.equals(openTime, that.openTime) && Objects.equals(closeTime, that.closeTime) && Objects.equals(breakStartTime, that.breakStartTime) && Objects.equals(breakEndTime, that.breakEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openTime, closeTime, breakStartTime, breakEndTime);
    }
}
