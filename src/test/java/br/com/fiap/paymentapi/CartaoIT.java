package br.com.fiap.paymentapi;

import br.com.fiap.paymentapi.dto.CartaoRequestDto;
import br.com.fiap.paymentapi.dto.CartaoResponseDto;
import br.com.fiap.paymentapi.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.YearMonth;

@Sql(scripts = "/sql/cartoes/cartoes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/cartoes/cartoes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartaoIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void criaCartao_ComDadosOk_RetornoSucessoHttp201() {

        CartaoResponseDto responseBody = webTestClient
                .post()
                .uri("/api/v1/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new CartaoRequestDto("12345678901", new BigDecimal(1000.00), "5678 6789 4572 9900", YearMonth.of(2024, 8), "123"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CartaoResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumero()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumero()).isEqualTo("**** **** **** 9900");
    }


    @Test
    public void criaCartao_ComCpfNaoExistente_RetornoErroHttp404() {

        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new CartaoRequestDto("12345678908", new BigDecimal(1000.00), "5678 6789 4572 9900", YearMonth.of(2024, 8), "123"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getMessage()).isEqualTo("CPF '12345678908' não existe no sistema");
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void criarCartao_ClienteJaPossuiCartaoCadastrado_RetornoErroHttp409() {

        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new CartaoRequestDto("34567890123", new BigDecimal(1000.00), "5678 6789 4572 9900", YearMonth.of(2024, 8), "123"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getMessage()).isEqualTo("CPF '34567890123' já tem cartão cadastrado");
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

    }

    @Test
    public void criarCartao_ComDadosInvalidos_RetornaErrorHttp422() {

        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/v1/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new CartaoRequestDto("", new BigDecimal(1000.00), "5678 6789 4572 9900", YearMonth.of(2024, 8), "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        webTestClient
                .post()
                .uri("/api/v1/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "bia@email.com", "123456789"))
                .bodyValue(new CartaoRequestDto("12345678901", new BigDecimal(0.0), "5678 6789 4572 9900", YearMonth.of(2024, 8), "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void criarCartao_SemToken_RetornarErrorMessageStatus401() {

        webTestClient
                .post()
                .uri("/api/v1/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CartaoRequestDto("12345678901", new BigDecimal(1000.00), "5678 6789 4572 9900", YearMonth.of(2024, 8), "123"))
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

    }


}
