package com.example.switchwon.payload.res;

import com.example.switchwon.domain.entity.Payments;
import com.example.switchwon.domain.enums.Currency;
import com.example.switchwon.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentApproveResponse {

    @Schema(description = "결제 IDX")
    private Long idx;

    @Schema(description = "결제 상태")
    private PaymentStatus status;

    @Schema(description = "결제 금액")
    private BigDecimal totalAmount;

    @Schema(description = "통화")
    private Currency currency;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "결제 일시")
    private LocalDateTime paymentDate;

    public PaymentApproveResponse(Payments payment) {
        this.idx = payment.getIdx();
        this.status = payment.getStatus();
        this.totalAmount = payment.getTotalAmount();
        this.currency = payment.getCurrencyCode();
        this.paymentDate = payment.getPaymentDate();
    }
}
