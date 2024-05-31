package com.example.switchwon.payload.req;

import com.example.switchwon.domain.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEstimateRequest {

    @Schema(description = "사용자 IDX")
    private Long userIdx;

    @Schema(description = "결제 금액")
    private BigDecimal amount;

    @Schema(description = "통화")
    private Currency currency;

    @Schema(description = "상점 IDX")
    private Long merchantId;

}
