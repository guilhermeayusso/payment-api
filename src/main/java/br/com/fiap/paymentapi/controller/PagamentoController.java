package br.com.fiap.paymentapi.controller;

import br.com.fiap.paymentapi.dto.PagamentoRequestDto;
import br.com.fiap.paymentapi.dto.PagamentoResponseDto;
import br.com.fiap.paymentapi.entity.Pagamento;
import br.com.fiap.paymentapi.exception.ErrorMessage;
import br.com.fiap.paymentapi.jwt.JwtUserDetails;
import br.com.fiap.paymentapi.mapper.PagamentoMapper;
import br.com.fiap.paymentapi.service.PagamentoService;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Pagamentos", description = "Contém todas as opereções relativas ao recurso de um pagamento")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PagamentoMapper pagamentoMapper;


    @Operation(summary = "Criar um novo Pagamento",
            description = "Recurso para criar um novo Pagamento. " +
                    "Requisição exige uso de um bearer token.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = PagamentoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Cartão não cadastrado",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "402", description = "Qualquer inconsistência nos dados do pagamento Ex. CVV invalido, Excedeu Limite, Data de Validade",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Token vazio ou invalido (Erro na Autenticação).",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = AuthenticationException.class)))
            })

    @PostMapping
    public ResponseEntity<PagamentoResponseDto> create(@Valid @RequestBody PagamentoRequestDto pagamentoRequestDto,
                                                       @AuthenticationPrincipal JwtUserDetails userDetails) {
        Pagamento pagamento = pagamentoService.create(pagamentoMapper.toEntity(pagamentoRequestDto));
        return ResponseEntity.status(201).body(pagamentoMapper.toDto(pagamento));
    }

    @Operation(summary = "Buscar um Pagamento",
            description = "Recurso para buscar um Pagamento. " +
                    "Requisição exige uso de um bearer token.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = PagamentoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não localizado",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Token vazio ou invalido (Erro na Autenticação).",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = AuthenticationException.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDto> getById(@PathVariable UUID id) {
        Pagamento pagamento = pagamentoService.getById(id);
        return ResponseEntity.ok(pagamentoMapper.toDto(pagamento));
    }
}
