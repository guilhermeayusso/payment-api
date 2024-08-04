package br.com.fiap.paymentapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class ClienteResponseDto implements Serializable {

    private Long id_cliente;
}
