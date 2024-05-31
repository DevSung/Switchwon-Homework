package com.example.switchwon.payload.req;

import com.example.switchwon.domain.entity.BalanceRecharge;
import com.example.switchwon.domain.entity.CardRechargeDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailRequest {
    @Schema(description = "카드사")
    private String cardCompany;

    @Schema(description = "카드번호")
    private String cardNumber;

    @Schema(description = "만료일")
    private String expiryDate;

    @Schema(description = "CVV")
    private String cvv;

    public CardRechargeDetail toEntity(BalanceRecharge balanceRecharge) {
        return CardRechargeDetail.builder()
                .balanceRecharge(balanceRecharge)
                .cardCompany(this.cardCompany)
                .cardNumber(this.cardNumber)
                .expiryDate(this.expiryDate)
                .cvv(this.cvv)
                .build();
    }

}
