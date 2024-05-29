package com.example.switchwon.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "account_balance")
public class AccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false, unique = true, updatable = false)
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
     * 잔액
     */
    @Column(name = "balance", precision = 15, scale = 2)
    private Double balance;

}
