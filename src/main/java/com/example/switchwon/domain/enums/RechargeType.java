package com.example.switchwon.domain.enums;

import lombok.Getter;

@Getter
public enum RechargeType {
    CARD("card"),
    CASH("cash");
    private final String code;

    RechargeType(String code) {
        this.code = code;
    }
}

