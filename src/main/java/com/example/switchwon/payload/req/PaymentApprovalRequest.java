package com.example.switchwon.payload.req;

import com.example.switchwon.domain.entity.Payments;
import com.example.switchwon.domain.entity.Users;
import com.example.switchwon.domain.enums.Currency;
import com.example.switchwon.domain.enums.PaymentStatus;
import com.example.switchwon.domain.enums.RechargeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApprovalRequest {

    @Schema(description = "사용자 IDX")
    private Long userIdx;

    @Schema(description = "결제 금액")
    private BigDecimal amount;

    @Schema(description = "통화")
    private Currency currency;

    @Schema(description = "상점 IDX")
    private Long merchantId;

    @Schema(description = "결제 방법")
    private RechargeType paymentMethod;

    @Schema(description = "결제 상세 정보")
    private PaymentDetailRequest paymentDetail;

    public Payments toEntity(Users user, BigDecimal totalAmount) {
        return Payments.builder()
                .user(user)
                .merchantIdx(this.merchantId)
                .currencyCode(this.currency)
                .totalAmount(totalAmount)
                .amount(this.amount)
                .fee(totalAmount.subtract(this.amount))
                .status(PaymentStatus.APPROVAL)
                .paymentDate(LocalDateTime.now())
                .build();
    }
}
