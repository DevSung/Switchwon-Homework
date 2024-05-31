package com.example.switchwon.service.payment.impl;

import com.example.switchwon.domain.entity.*;
import com.example.switchwon.domain.enums.Currency;
import com.example.switchwon.domain.enums.RechargeStatus;
import com.example.switchwon.domain.enums.RechargeType;
import com.example.switchwon.domain.repository.*;
import com.example.switchwon.exception.ApiResponseException;
import com.example.switchwon.payload.req.PaymentApprovalRequest;
import com.example.switchwon.payload.req.PaymentEstimateRequest;
import com.example.switchwon.payload.res.BalanceResponse;
import com.example.switchwon.payload.res.PaymentApproveResponse;
import com.example.switchwon.payload.res.PaymentEstimateResponse;
import com.example.switchwon.service.fee.FeeCalculationStrategy;
import com.example.switchwon.service.fee.FeeCalculationStrategyFactory;
import com.example.switchwon.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.switchwon.exception.ExceptionCode.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final AccountBalanceRepository accountBalanceRepository;
    private final MerchantRepository merchantRepository;
    private final CurrencyCodeRepository currencyCodeRepository;
    private final BalanceRechargeRepository balanceRechargeRepository;
    private final PaymentsRepository paymentsRepository;
    private final UsersRepository usersRepository;

    /**
     * 잔액 조회
     *
     * @param userIdx 사용자 IDX
     * @return BalanceResponse (잔액 조회 응답)
     */
    @Override
    @Transactional(readOnly = true)
    public List<BalanceResponse> getBalance(Long userIdx) {
        Users user = getUsers(userIdx);
        // 잔액이 없는 경우 빈 리스트 반환
        if (CollectionUtils.isEmpty(user.getAccountBalances())) {
            return Collections.emptyList();
        }
        return BalanceResponse.by(user.getAccountBalances());
    }

    /**
     * 결제 예상 금액 조회
     *
     * @param request 결제 예상 금액 조회 요청 request
     * @return PaymentEstimateResponse (결제 예상 금액 조회 응답)
     */
    @Override
    @Transactional(readOnly = true)
    public PaymentEstimateResponse createEstimate(PaymentEstimateRequest request) {
        Users user = getUsers(request.getUserIdx());
        Merchant merchant = getMerchant(request.getMerchantId());
        CurrencyCode currencyCode = getCurrencyCode(request.getCurrency());
        BigDecimal fee = calculateFee(currencyCode.getAllowDecimal(), request.getAmount(), merchant.getFee());

        // 총 금액 계산 (결제 금액 + 수수료)
        BigDecimal totalAmount = request.getAmount().add(fee);

        return new PaymentEstimateResponse(totalAmount, fee, currencyCode.getCurrencyCode());
    }

    /**
     * 결제 승인 요청
     *
     * @param request 결제 승인 요청 request
     * @return PaymentApproveResponse (결제 승인 응답)
     */
    @Override
    @Transactional
    public PaymentApproveResponse approvePayment(PaymentApprovalRequest request) {
        Users user = getUsers(request.getUserIdx());
        Merchant merchant = getMerchant(request.getMerchantId());
        CurrencyCode currencyCode = getCurrencyCode(request.getCurrency());
        BigDecimal fee = calculateFee(currencyCode.getAllowDecimal(), request.getAmount(), merchant.getFee());

        // 총 금액 계산 (결제 금액 + 수수료)
        BigDecimal totalAmount = request.getAmount().add(fee);

        AccountBalance balance = user.getAccountBalances().stream()
                .filter(b -> b.getCurrencyCode().getCode().equals(currencyCode.getCurrencyCode().getCode()))
                .findFirst()
                .orElse(null);

        // 잔액이 없거나 결제 금액보다 잔액이 적은 경우 에러 발생
        // -1 : 잔액이 부족한 경우, 0 : 잔액이 정확한 경우, 1 : 잔액이 충분한 경우
        if (balance == null || balance.getBalance().compareTo(totalAmount) < 0) {
            BigDecimal shortfall = totalAmount.subtract(balance != null ? balance.getBalance() : BigDecimal.ZERO);
            BalanceRecharge recharge = chargeBalance(request, user, balance, shortfall);
            if (recharge == null) {
                throw new ApiResponseException(ERROR_NOT_FOUND, "Your balance is insufficient");
            } else {
                // 사용자 잔액 업데이트
                updateAccountBalance(shortfall, Objects.requireNonNull(balance));
            }
        }
        // 잔액 차감
        deductBalance(balance, totalAmount);
        // 결제 정보 저장
        Payments payment = savePayments(user, request, totalAmount);
        return new PaymentApproveResponse(payment);
    }

    /**
     * 잔액 더하기
     */
    private void updateAccountBalance(BigDecimal shortfall, AccountBalance balance) {
        balance.addBalance(shortfall);
        accountBalanceRepository.saveAndFlush(balance);
    }

    /**
     * 잔여 금액 차감
     */
    private void deductBalance(AccountBalance balance, BigDecimal totalAmount) {
        balance.deductBalance(totalAmount);
        accountBalanceRepository.save(balance);
    }

    /**
     * 결제 정보 저장
     */
    private Payments savePayments(Users users, PaymentApprovalRequest request, BigDecimal totalAmount) {
        Payments payments = request.toEntity(users, totalAmount);
        return paymentsRepository.save(payments);
    }

    /**
     * 잔액 충전
     *
     * @param balance     잔여 잔액
     * @param totalAmount 결제 금액
     */
    private BalanceRecharge chargeBalance(PaymentApprovalRequest request, Users user, AccountBalance balance, BigDecimal totalAmount) {
        BigDecimal chargeAmount;
        if (balance == null) {
            chargeAmount = totalAmount.negate().setScale(2, RoundingMode.HALF_UP); // 잔액이 없는 경우
        } else {
            BigDecimal remainingBalance = balance.getBalance().subtract(totalAmount);
            chargeAmount = remainingBalance.negate().setScale(2, RoundingMode.HALF_UP); // 잔액이 있는 경우
        }
        return createRechargeHistory(request, user, chargeAmount);
    }

    /**
     * 잔액 충전
     */
    private BalanceRecharge createRechargeHistory(PaymentApprovalRequest request, Users user, BigDecimal chargeAmount) {
        BalanceRecharge balanceRecharge;
        if (request.getPaymentMethod().getCode().equals("CARD")) {
            balanceRecharge = BalanceRecharge.builder()
                    .user(user)
                    .currencyCode(request.getCurrency())
                    .amount(chargeAmount)
                    .rechargeType(RechargeType.CARD)
                    .status(RechargeStatus.APPROVAL)
                    .rechargeDate(LocalDateTime.now())
                    .build();

            CardRechargeDetail detail = request.getPaymentDetail().toEntity(balanceRecharge);
            balanceRecharge.setCardRechargeDetail(detail);

            return balanceRechargeRepository.save(balanceRecharge);
        }
        return null;
    }

    /**
     * 사용자 조회
     */
    private Users getUsers(Long userIdx) {
        return usersRepository.findById(userIdx)
                .orElseThrow(() -> new ApiResponseException(ERROR_NOT_FOUND, "User not found."));
    }

    /**
     * 상점 조회
     */
    private Merchant getMerchant(Long merchantId) {
        return merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ApiResponseException(ERROR_NOT_FOUND, "Store not found."));
    }

    /**
     * 통화 조회
     */
    private CurrencyCode getCurrencyCode(Currency currency) {
        return currencyCodeRepository.findByCurrencyCode(currency)
                .orElseThrow(() -> new ApiResponseException(ERROR_NOT_FOUND, "Currency not found."));
    }

    /**
     * 수수료 계산
     */
    private BigDecimal calculateFee(boolean allowDecimal, BigDecimal amount, BigDecimal fee) {
        FeeCalculationStrategy strategy = FeeCalculationStrategyFactory.getStrategy(allowDecimal);
        return strategy.calculateFee(amount, fee);
    }

}
