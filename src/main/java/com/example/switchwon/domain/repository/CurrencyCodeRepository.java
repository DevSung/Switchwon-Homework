package com.example.switchwon.domain.repository;

import com.example.switchwon.domain.entity.CurrencyCode;
import com.example.switchwon.domain.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyCodeRepository extends JpaRepository<CurrencyCode, Long> {
    Optional<CurrencyCode> findByCurrencyCode(Currency currencyCode);
}
