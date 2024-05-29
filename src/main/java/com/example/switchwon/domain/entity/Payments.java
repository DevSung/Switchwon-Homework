package com.example.switchwon.domain.entity;

import com.example.switchwon.domain.enums.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private User user;

    /**
     * 상점 IDX
     */
    @Column(name = "merchant_idx")
    private Long merchantIdx;

    /**
     * 통화 코드
     */
    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    /**
     * 총 금액
     */
    @Column(name = "total_amount", precision = 15, scale = 2)
    private Double totalAmount;

    /**
     * 결제 금액
     */
    @Column(name = "amount", precision = 15, scale = 2)
    private Double amount;

    /**
     * 수수료
     */
    @Column(name = "fee", precision = 5, scale = 2)
    private Double fee;

    /**
     * 결제 상태
     */
    @Column(name = "status", length = 3)
    private PaymentStatus status;

    /**
     * 결제 일시
     */
    @Column(name = "payment_date", updatable = false)
    private LocalDateTime paymentDate;
}
