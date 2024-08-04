package br.com.fiap.paymentapi.dto;

import br.com.fiap.paymentapi.annotations.CardNumberMask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoResponseDto {


    private String cpf;
    private BigDecimal limite;
    @CardNumberMask
    private String numero;
    private YearMonth dataValidade;
    private String cvv;
}
