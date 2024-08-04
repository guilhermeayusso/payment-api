package br.com.fiap.paymentapi.controller;

import br.com.fiap.paymentapi.dto.CartaoRequestDto;
import br.com.fiap.paymentapi.dto.CartaoResponseDto;
import br.com.fiap.paymentapi.entity.Cartao;
import br.com.fiap.paymentapi.exception.ErrorMessage;
import br.com.fiap.paymentapi.jwt.JwtUserDetails;
import br.com.fiap.paymentapi.mapper.CartaoMapper;
import br.com.fiap.paymentapi.service.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cartões", description = "Contém todas as opereções relativas ao recurso de um cartão")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;
    private final CartaoMapper cartaoMapper;

    @Operation(summary = "Criar um novo cartão",
            description = "Recurso para criar um novo cartão vinculado a um cliente cadastrado. " +
                    "Requisição exige uso de um bearer token.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CartaoResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Cliente CPF já possui um cartão cadastrado",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Adição de cartão requer cliente cadastrado.",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Token vazio ou invalido (Erro na Autenticação).",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = AuthenticationException.class)))
            })

    @PostMapping
    public ResponseEntity<CartaoResponseDto> cadastrar(@Valid @RequestBody CartaoRequestDto dto,
                                                       @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cartao cartao = cartaoService.create(cartaoMapper.toEntity(dto));
        return ResponseEntity.status(201).body(cartaoMapper.toDto(cartao));

    }
}
