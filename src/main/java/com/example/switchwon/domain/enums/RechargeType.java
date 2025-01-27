package com.example.switchwon.domain.enums;

import lombok.Getter;

@Getter
public enum RechargeType {
    CARD("CARD"),
    CASH("CASH");
    private final String code;

    RechargeType(String code) {
        this.code = code;
    }
}

