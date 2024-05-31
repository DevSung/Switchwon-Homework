package com.example.switchwon.payload.res;

import com.example.switchwon.domain.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentEstimateResponse {

    @Schema(description = "결제 예상 금액")
    private BigDecimal estimatedTotal;

    @Schema(description = "수수료")
    private BigDecimal fee;

    @Schema(description = "통화")
    private Currency currency;

    public PaymentEstimateResponse(BigDecimal estimatedTotal, BigDecimal fee, Currency currency) {
        this.estimatedTotal = estimatedTotal;
        this.fee = fee;
        this.currency = currency;
    }

}
