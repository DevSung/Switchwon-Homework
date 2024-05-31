package com.example.switchwon.controller;

import com.example.switchwon.payload.common.ResponseData;
import com.example.switchwon.payload.req.PaymentApprovalRequest;
import com.example.switchwon.payload.req.PaymentEstimateRequest;
import com.example.switchwon.payload.res.BalanceResponse;
import com.example.switchwon.payload.res.PaymentApproveResponse;
import com.example.switchwon.payload.res.PaymentEstimateResponse;
import com.example.switchwon.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
            summary = "잔액 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "잔액 조회 성공",
                            content = @Content(schema = @Schema(implementation = BalanceResponse.class))
                    )
            }
    )
    @GetMapping("/balance/{userIdx}")
    public ResponseEntity<Object> getBalance(@PathVariable Long userIdx) {
        return ResponseData.success(paymentService.getBalance(userIdx));
    }

    @Operation(
            summary = "결제 예상 금액 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "결제 예상 금액 조회 성공",
                            content = @Content(schema = @Schema(implementation = PaymentEstimateResponse.class))
                    )
            }
    )
    @PostMapping("/estimate")
    public ResponseEntity<Object> createEstimate(@RequestBody PaymentEstimateRequest request) {
        return ResponseData.success(paymentService.createEstimate(request));
    }

    @Operation(
            summary = "결제 승인 요쳥",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "결제 승인 성공",
                            content = @Content(schema = @Schema(implementation = PaymentApproveResponse.class))
                    )
            }
    )
    @PostMapping("/approval")
    public ResponseEntity<Object> approvePayment(@RequestBody PaymentApprovalRequest request) {
        return ResponseData.success(paymentService.approvePayment(request));
    }

}
