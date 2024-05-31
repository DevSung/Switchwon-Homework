package com.example.switchwon.service.payment;

import com.example.switchwon.payload.req.PaymentApprovalRequest;
import com.example.switchwon.payload.req.PaymentEstimateRequest;
import com.example.switchwon.payload.res.BalanceResponse;
import com.example.switchwon.payload.res.PaymentApproveResponse;
import com.example.switchwon.payload.res.PaymentEstimateResponse;

import java.util.List;

public interface PaymentService {
    List<BalanceResponse> getBalance(Long userIdx);

    PaymentEstimateResponse createEstimate(PaymentEstimateRequest request);

    PaymentApproveResponse approvePayment(PaymentApprovalRequest request);
}
