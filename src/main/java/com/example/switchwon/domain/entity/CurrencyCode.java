package com.example.switchwon.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "currency_code")
public class CurrencyCode {

    @Id
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "allow_decimal", nullable = false)
    private Boolean allowDecimal;

}
