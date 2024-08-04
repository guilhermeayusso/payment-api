package br.com.fiap.paymentapi.dto;

import br.com.fiap.paymentapi.enums.Estado;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDto implements Serializable {

    @NotNull(message = "CPF deve ser preenchido")
    @NotBlank(message = "CPF deve ser preenchido")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "Nome deve ser preenchido")
    @Size(max = 100, message = "Nome não pode ter mais de 100 caracteres")
    private String nome;

    @NotBlank(message = "Email deve ser preenchido")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Telefone deve ser preenchido")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos numéricos")
    private String telefone;

    @NotBlank(message = "Rua deve ser preenchida")
    @Size(max = 150, message = "Rua não pode ter mais de 150 caracteres")
    private String rua;

    @NotBlank(message = "Cidade deve ser preenchida")
    @Size(max = 100, message = "Cidade não pode ter mais de 100 caracteres")
    private String cidade;

    @NotNull(message = "Estado deve ser preenchido")
    private Estado estado;

    @NotBlank(message = "CEP deve ser preenchido")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 99999-999")
    private String cep;

    @NotBlank(message = "País deve ser preenchido")
    @Size(max = 100, message = "País não pode ter mais de 100 caracteres")
    private String pais;
}