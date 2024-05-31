package com.example.switchwon.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "card_recharge_detail")
public class CardRechargeDetail {

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
     * 카드 회사
     */
    @Column(name = "card_company", length = 20)
    private String cardCompany;

    /**
     * 카드 번호
     */
    @Column(name = "card_number", length = 20)
    private String cardNumber;

    /**
     * 카드 유효기간
     */
    @Column(name = "expiry_date", length = 5)
    private String expiryDate;

    /**
     * CVS 번호
     */
    @Column(name = "cvv", length = 3)
    private String cvv;

    @Builder
    public CardRechargeDetail(BalanceRecharge balanceRecharge, String cardCompany, String cardNumber, String expiryDate, String cvv) {
        this.balanceRecharge = balanceRecharge;
        this.cardCompany = cardCompany;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

}
