package com.example.switchwon.domain.entity;

import com.example.switchwon.domain.enums.Currency;
import com.example.switchwon.exception.ApiResponseException;
import com.example.switchwon.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_balance")
public class AccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false, unique = true, updatable = false)
    private Long idx;

    /**
     * 사용자 정보
     */
    @Setter
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
     * 잔액
     */
    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance;

    public void deductBalance(BigDecimal amount) {
        // 잔액이 차감하려는 금액보다 적은지 검증
        if (this.balance.compareTo(amount) < 0) {
            throw new ApiResponseException(ExceptionCode.ERROR_BAD_REQUEST, "your balance is insufficient.");
        }
        // 잔액 차감
        this.balance = this.balance.subtract(amount);
    }

    public void addBalance(BigDecimal amount) {
        // 잔액 추가
        this.balance = this.balance.add(amount);
    }
}
