package com.example.switchwon.domain.enums;

import lombok.Getter;

@Getter
public enum RechargeStatus {
    CARD("card"),
    CASH("cash");
    private final String code;

    RechargeStatus(String code) {
        this.code = code;
    }
}

