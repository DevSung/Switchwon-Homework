package com.example.switchwon.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cash_recharge_detail")
public class CashRechargeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long idx;

    /**
     * 충전 IDX
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recharge_idx")
    private BalanceRecharge balanceRecharge;

    /**
     * 현금 영수증 번호
     */
    @Column(name = "receipt_number", length = 20)
    private String receiptNumber;

}
