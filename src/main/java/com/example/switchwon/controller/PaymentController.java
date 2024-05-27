package com.example.switchwon.controller;

import com.example.switchwon.payload.common.ResponseData;
import com.example.switchwon.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "잔액 조회")
    @GetMapping("/balance/{userId}")
    public ResponseEntity<Object> getBalance(@PathVariable Long userId) {
        return ResponseData.success(paymentService.getBalance(userId));
    }

    @Operation(summary = "결제 예상 금액 조회")
    @PostMapping("/estimate")
    public ResponseEntity<Object> createEstimate() {
        return ResponseData.success(null);
    }

    @Operation(summary = "결제 승인 요쳥")
    @PostMapping("approval")
    public ResponseEntity<Object> approvePayment() {
        return ResponseData.success(null);
    }
}
