package com.example.switchwon.domain.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    APPROVAL("approval"), // 충전 승인
    FAIL("fail"), // 충전 실패
    CANCEL("cancel"), // 충전 취소
    REFUND("refund"); // 충전 환불

    private final String code;

    PaymentStatus(String code) {
        this.code = code;
    }
}

