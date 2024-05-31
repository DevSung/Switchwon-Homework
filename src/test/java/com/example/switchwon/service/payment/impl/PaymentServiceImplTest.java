package com.example.switchwon.service.payment.impl;

import com.example.switchwon.domain.entity.*;
import com.example.switchwon.domain.enums.Currency;
import com.example.switchwon.domain.enums.PaymentStatus;
import com.example.switchwon.domain.enums.RechargeType;
import com.example.switchwon.domain.repository.*;
import com.example.switchwon.exception.ApiResponseException;
import com.example.switchwon.payload.req.PaymentApprovalRequest;
import com.example.switchwon.payload.req.PaymentDetailRequest;
import com.example.switchwon.payload.req.PaymentEstimateRequest;
import com.example.switchwon.payload.res.BalanceResponse;
import com.example.switchwon.payload.res.PaymentApproveResponse;
import com.example.switchwon.payload.res.PaymentEstimateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private AccountBalanceRepository accountBalanceRepository;

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private CurrencyCodeRepository currencyCodeRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PaymentsRepository paymentsRepository;

    @Mock
    private BalanceRechargeRepository balanceRechargeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void 사용자가_없는_경우_테스트() {
        // Arrange
        Long userId = 1L;
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApiResponseException.class, () -> paymentService.getBalance(userId));
    }

    @Test
    void 잔액이_없는_사용자_테스트() {
        // Arrange
        Long userId = 1L;
        Users user = new Users();
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        List<BalanceResponse> result = paymentService.getBalance(userId);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void 잔액이_있는_사용자_테스트() {
        // Arrange
        Long userId = 1L;
        Users user = new Users();
        List<AccountBalance> accountBalances = Arrays.asList(
                new AccountBalance(1L, user, Currency.USD, new BigDecimal("100.15")),
                new AccountBalance(2L, user, Currency.KRW, new BigDecimal("200"))
        );

        user.setAccountBalances(accountBalances); // 여기서는 setBalanceList 메소드로 잔액 목록을 설정합니다.
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        List<BalanceResponse> result = paymentService.getBalance(userId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(new BigDecimal("100.15"), result.get(0).getBalance());
        assertEquals("USD", result.get(0).getCurrency());

        assertEquals(new BigDecimal("200"), result.get(1).getBalance());
        assertEquals("KRW", result.get(1).getCurrency());
    }

    @Test
    void 예상_금액_테스트() {
        // Arrange
        // 사용자 잔액 설정
        Long userId = 1L;
        Users user = new Users();
        List<AccountBalance> accountBalances = Arrays.asList(
                new AccountBalance(1L, user, Currency.USD, new BigDecimal("100.15")),
                new AccountBalance(2L, user, Currency.KRW, new BigDecimal("200"))
        );

        user.setAccountBalances(accountBalances);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        // 상점 수수료 설정
        BigDecimal fee = new BigDecimal("3.00");
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(new Merchant(1L, "상점1", fee)));

        // 통화 코드 설정
        Currency currency = Currency.USD;
        when(currencyCodeRepository.findByCurrencyCode(currency)).thenReturn(Optional.of(new CurrencyCode(currency, true)));

        // Act
        PaymentEstimateRequest request = new PaymentEstimateRequest(userId, new BigDecimal("100.15"), currency, 1L);
        PaymentEstimateResponse result = paymentService.createEstimate(request);

        // Assert
        assertEquals(new BigDecimal("103.15"), result.getEstimatedTotal());
        assertEquals(fee, result.getFee());
        assertEquals(currency, result.getCurrency());
    }

    @Test
    void 결제_승인_요청_테스트() {// Arrange
        // Arrange
        Long userId = 1L;
        Users user = new Users();
        List<AccountBalance> accountBalances = Arrays.asList(
                new AccountBalance(1L, user, Currency.USD, new BigDecimal("100.15")),
                new AccountBalance(2L, user, Currency.KRW, new BigDecimal("200"))
        );

        user.setAccountBalances(accountBalances);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        BigDecimal fee = new BigDecimal("3.00");
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(new Merchant(1L, "상점1", fee)));

        Currency currency = Currency.USD;
        when(currencyCodeRepository.findByCurrencyCode(currency)).thenReturn(Optional.of(new CurrencyCode(currency, true)));

        Payments paymentToSave = new Payments(user, 1L, Currency.USD, new BigDecimal("103.15"), new BigDecimal("100.15"), fee, PaymentStatus.APPROVAL, LocalDateTime.now());
        when(paymentsRepository.save(any(Payments.class))).thenReturn(paymentToSave);

        // Act
        PaymentDetailRequest detailRequest = new PaymentDetailRequest("현대카드", "1234-1234-1234-1234", "12/12", "123");
        PaymentApprovalRequest request = new PaymentApprovalRequest(1L, new BigDecimal("90.00"), currency, 1L, RechargeType.CARD, detailRequest);
        PaymentApproveResponse result = paymentService.approvePayment(request);

        // Assert
        assertEquals(new BigDecimal("103.15"), result.getTotalAmount());
    }

    @Test
    void 결제_요청_잔액_부족_테스트() {
        // Arrange
        Long userId = 1L;
        Users user = new Users();
        List<AccountBalance> accountBalances = Arrays.asList(
                new AccountBalance(1L, user, Currency.USD, new BigDecimal("50.00")),
                new AccountBalance(2L, user, Currency.KRW, new BigDecimal("200"))
        );

        user.setAccountBalances(accountBalances);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        BigDecimal fee = new BigDecimal("3.00");
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(new Merchant(1L, "상점1", fee)));

        Currency currency = Currency.USD;
        when(currencyCodeRepository.findByCurrencyCode(currency)).thenReturn(Optional.of(new CurrencyCode(currency, true)));

        PaymentDetailRequest detailRequest = new PaymentDetailRequest("현대카드", "1234-1234-1234-1234", "12/12", "123");
        PaymentApprovalRequest request = new PaymentApprovalRequest(1L, new BigDecimal("100.15"), currency, 1L, RechargeType.CARD, detailRequest);

        // Act & Assert
        ApiResponseException exception = assertThrows(ApiResponseException.class, () -> {
            paymentService.approvePayment(request);
        });

        assertThrows(ApiResponseException.class, () -> paymentService.approvePayment(request));
    }
}