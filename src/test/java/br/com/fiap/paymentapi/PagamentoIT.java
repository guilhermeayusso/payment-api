package br.com.fiap.paymentapi;

import br.com.fiap.paymentapi.dto.PagamentoRequestDto;
import br.com.fiap.paymentapi.dto.PagamentoResponseDto;
import br.com.fiap.paymentapi.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

@Sql(scripts = "/sql/pagamentos/pagamentos-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/pagamentos/pagamentos-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PagamentoIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void criaPagamento_ComDadosOk_RetornoSucessoHttp201() {
        PagamentoResponseDto responseBody = webTestClient
                .post()
                .uri("/api/v1/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new PagamentoRequestDto("23456789012", new BigDecimal("7500.49"), "2345 6789 0123 4567", "07/25", "456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PagamentoResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getChave_pagamento()).isNotNull();
    }

    @Test
    public void criaPagamento_ComCPFInvalido_RetornoErroHttp402() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new PagamentoRequestDto("23456789013", new BigDecimal("7500.49"), "2345 6789 0123 4567", "07/25", "456"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.PAYMENT_REQUIRED)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getMessage()).isEqualTo("CPF do cartão incorreta. ");
    }

    @Test
    public void criaPagamento_ComValorMaiorQueLimite_RetornoErroHttp402() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new PagamentoRequestDto("23456789012", new BigDecimal("7500.51"), "2345 6789 0123 4567", "07/25", "456"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.PAYMENT_REQUIRED)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getMessage()).isEqualTo("Valor da compra não pode ser maior que o Limite. ");
    }

    @Test
    public void criaPagamento_ComCartaoInvalido_RetornoErroHttp404() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new PagamentoRequestDto("23456789012", new BigDecimal("7500.49"), "0000 6789 0123 4567", "07/25", "456"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getMessage()).isEqualTo("Cartão inexistente. ");
    }

    @Test
    public void criaPagamento_ComDataDeValidadeInvalido_RetornoErroHttp402() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new PagamentoRequestDto("23456789012", new BigDecimal("7500.49"), "2345 6789 0123 4567", "06/25", "456"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.PAYMENT_REQUIRED)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getMessage()).isEqualTo("Data da validade do cartão incorreta. ");
    }

    @Test
    public void criaPagamento_ComCvvInvalido_RetornoErroHttp402() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new PagamentoRequestDto("23456789012", new BigDecimal("7500.49"), "2345 6789 0123 4567", "07/25", "450"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.PAYMENT_REQUIRED)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getMessage()).isEqualTo("CVV do cartão incorreta. ");
    }

    @Test
    public void BuscarPagamento_IdValido_RetornoSucessoHttp200() {
        webTestClient.get()
                .uri("/api/v1/pagamentos/{id}", "550e8400-e29b-41d4-a716-446655440000")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("chave_pagamento").isEqualTo("550e8400-e29b-41d4-a716-446655440000")
                .jsonPath("valor").isEqualTo("3000.0")
                .jsonPath("numero").isEqualTo("**** **** **** 6789");
    }

    @Test
    public void BuscarPagamento_IdInvalido_RetornoErroHttp404() {
        webTestClient.get()
                .uri("/api/v1/pagamentos/{id}", "550e8300-e29b-41d4-a716-446655440000")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/pagamentos/550e8300-e29b-41d4-a716-446655440000")
                .jsonPath("method").isEqualTo("GET");
    }
}
