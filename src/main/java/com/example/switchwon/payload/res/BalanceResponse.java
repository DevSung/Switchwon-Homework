package com.example.switchwon.payload.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceResponse {

    @Schema(name = "유저 pk")
    private Long userId;

    @Schema(name = "잔액")
    private Float balance;

    @Schema(name = "통화")
    private String currency;

    public BalanceResponse(Long userId, Float balance, String currency) {
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
    }

}
