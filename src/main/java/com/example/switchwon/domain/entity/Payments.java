package com.example.switchwon.domain.entity;

import com.example.switchwon.domain.enums.Currency;
import com.example.switchwon.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long idx;

    /**
     * 사용자 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private Users user;

    /**
     * 상점 IDX
     */
    @Column(name = "merchant_idx")
    private Long merchantIdx;

    /**
     * 통화 코드
     */
    @Column(name = "currency_code", length = 3)
    private Currency currencyCode;

    /**
     * 총 금액
     */
    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    /**
     * 결제 금액
     */
    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    /**
     * 수수료
     */
    @Column(name = "fee", precision = 3, scale = 1)
    private BigDecimal fee;

    /**
     * 결제 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 3)
    private PaymentStatus status;

    /**
     * 결제 일시
     */
    @Column(name = "payment_date", updatable = false)
    private LocalDateTime paymentDate;

    @Builder
    public Payments(Users user, Long merchantIdx, Currency currencyCode, BigDecimal totalAmount, BigDecimal amount, BigDecimal fee, PaymentStatus status, LocalDateTime paymentDate) {
        this.user = user;
        this.merchantIdx = merchantIdx;
        this.currencyCode = currencyCode;
        this.totalAmount = totalAmount;
        this.amount = amount;
        this.fee = fee;
        this.status = status;
        this.paymentDate = paymentDate;
    }
}
