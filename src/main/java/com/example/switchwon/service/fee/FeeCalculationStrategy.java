package com.example.switchwon.service.fee;

import java.math.BigDecimal;

public interface FeeCalculationStrategy {
    BigDecimal calculateFee(BigDecimal amount, BigDecimal feeRate);
}
