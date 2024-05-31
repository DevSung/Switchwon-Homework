package com.example.switchwon.domain.enums;

import lombok.Getter;

@Getter
public enum Currency {
    USD("USD"),
    KRW("KRW");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

}
