package com.example.switchwon.service.fee;

import com.example.switchwon.service.fee.impl.RoundingFeeCalculationStrategy;
import com.example.switchwon.service.fee.impl.TruncationFeeCalculationStrategy;

public class FeeCalculationStrategyFactory {

    /**
     * 수수료 계산 전략을 반환
     *
     * @param allowDecimal 소수점 허용 여부
     * @return FeeCalculationStrategy 구현체
     */
    public static FeeCalculationStrategy getStrategy(boolean allowDecimal) {
        if (allowDecimal) {
            return new RoundingFeeCalculationStrategy();
        } else {
            return new TruncationFeeCalculationStrategy();
        }
    }

}
