package com.example.switchwon.domain.entity;

import com.example.switchwon.domain.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency_code")
public class CurrencyCode {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", nullable = false, length = 3)
    private Currency currencyCode;

    @Column(name = "allow_decimal", nullable = false)
    private Boolean allowDecimal;

}
