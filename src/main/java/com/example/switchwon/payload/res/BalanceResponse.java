package com.example.switchwon.payload.res;

import com.example.switchwon.domain.entity.AccountBalance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class BalanceResponse {

    @Schema(description = "유저 pk")
    private Long userIdx;

    @Schema(description = "잔액")
    private BigDecimal balance;

    @Schema(description = "통화")
    private String currency;

    public BalanceResponse(AccountBalance accountBalance) {
        this.userIdx = accountBalance.getUser().getIdx();
        this.balance = accountBalance.getBalance();
        this.currency = accountBalance.getCurrencyCode().getCode();
    }

    public static List<BalanceResponse> by(List<AccountBalance> accountBalances) {
        return accountBalances.stream().map(BalanceResponse::new).toList();
    }

}
