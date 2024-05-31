package com.example.switchwon.domain.entity;

import com.example.switchwon.domain.enums.Currency;
import com.example.switchwon.domain.enums.RechargeStatus;
import com.example.switchwon.domain.enums.RechargeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "balance_recharge")
public class BalanceRecharge {

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
     * 통화 코드
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", length = 3)
    private Currency currencyCode;

    /**
     * 금액
     */
    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    /**
     * 충전 타입
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "recharge_type", length = 3)
    private RechargeType rechargeType;

    /**
     * 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 3)
    private RechargeStatus status;

    /**
     * 충전일
     */
    @Column(name = "recharge_date", updatable = false)
    private LocalDateTime rechargeDate;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "balanceRecharge", cascade = CascadeType.ALL)
    private CardRechargeDetail cardRechargeDetail;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "balanceRecharge", cascade = CascadeType.ALL)
    private CashRechargeDetail cashRechargeDetail;

    @Builder
    public BalanceRecharge(Users user, Currency currencyCode, BigDecimal amount, RechargeType rechargeType, RechargeStatus status, LocalDateTime rechargeDate) {
        this.user = user;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.rechargeType = rechargeType;
        this.status = status;
        this.rechargeDate = rechargeDate;
    }
}
