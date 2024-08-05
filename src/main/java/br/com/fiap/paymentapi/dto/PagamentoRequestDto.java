package br.com.fiap.paymentapi.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.YearMonth;

/**
 * DTO for {@link br.com.fiap.paymentapi.entity.Pagamento}
 */
@Value
public class PagamentoRequestDto implements Serializable {
    String cpf;
    BigDecimal valor;
    String numero;
    YearMonth dataValidade;
    String cvv;
}