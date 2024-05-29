package com.example.switchwon.domain.entity;

import com.example.switchwon.domain.enums.RechargeStatus;
import com.example.switchwon.domain.enums.RechargeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private User user;

    /**
     * 통화 코드
     */
    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    /**
     * 금액
     */
    @Column(name = "amount", precision = 15, scale = 2)
    private Double amount;

    /**
     * 충전 타입
     */
    @Column(name = "recharge_type", length = 3)
    private RechargeType rechargeType;

    /**
     * 상태
     */
    @Column(name = "status", length = 3)
    private RechargeStatus status;

    /**
     * 충전일
     */
    @Column(name = "recharge_date", updatable = false)
    private LocalDateTime rechargeDate;

}
