package br.com.fiap.paymentapi.dto;

import br.com.fiap.paymentapi.annotations.CardNumberMask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoResponseDto {

    private UUID chave_pagamento;
    private BigDecimal valor;
    @CardNumberMask
    private String numero;
}
