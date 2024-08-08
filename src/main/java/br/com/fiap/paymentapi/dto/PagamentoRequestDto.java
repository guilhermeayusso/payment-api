package br.com.fiap.paymentapi.dto;

import br.com.fiap.paymentapi.annotations.DataDeValidade;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequestDto implements Serializable {

    @NotNull(message = "CPF deve ser preenchido")
    @NotBlank(message = "CPF deve ser preenchido")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos numéricos")
    private String cpf;

    @NotNull(message = "Limite deve ser preenchido")
    @Positive(message = "Limite deve ser um valor positivo")
    @DecimalMin(value = "0.0", inclusive = false, message = "Limite deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "Número do cartão deve ser preenchido")
    @Size(min = 19, max = 19, message = "Número do cartão deve ter exatamente 19 caracteres, incluindo espaços")
    @Pattern(regexp = "\\d{4} \\d{4} \\d{4} \\d{4}", message = "Número do cartão deve estar no formato DDDD DDDD DDDD DDDD")
    private String numero;

    @NotNull(message = "Data de validade do cartão deve ser preenchido")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$", message = "Data de validade deve estar no formato MM/yy")
    @DataDeValidade
    private String dataValidade;

    @NotNull(message = "CVV deve ser preenchido")
    @Pattern(regexp = "\\d{3}", message = "CVV deve conter exatamente 3 dígitos numéricos")
    private String cvv;
}