package com.example.switchwon.service;

import com.example.switchwon.payload.res.BalanceResponse;

public interface PaymentService {
    BalanceResponse getBalance(Long userId);
}
