package com.example.switchwon.service.impl;

import com.example.switchwon.payload.res.BalanceResponse;
import com.example.switchwon.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    /**
     * 잔액 조회
     * @param userId 사용자 ID
     * @return 잔액
     */
    @Override
    @Transactional(readOnly = true)
    public BalanceResponse getBalance(Long userId) {
        return null;
    }
}
