package com.example.switchwon.service.fee.impl;

import com.example.switchwon.service.fee.FeeCalculationStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TruncationFeeCalculationStrategy implements FeeCalculationStrategy {

    // 수수료 계산
    // 소수점 버림
    @Override
    public BigDecimal calculateFee(BigDecimal amount, BigDecimal feeRate) {
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(feeRate.doubleValue() / 100));
        return fee.setScale(0, RoundingMode.DOWN);
    }

}
