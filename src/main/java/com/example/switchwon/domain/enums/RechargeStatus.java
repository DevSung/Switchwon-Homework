package com.example.switchwon.domain.enums;

import lombok.Getter;

@Getter
public enum RechargeStatus {
    APPROVAL("approval"), // 충전 승인
    FAIL("fail");  // 충전 실패

    private final String code;

    RechargeStatus(String code) {
        this.code = code;
    }
}

