package br.com.fiap.paymentapi.controller;

import br.com.fiap.paymentapi.dto.ClienteRequestDto;
import br.com.fiap.paymentapi.dto.ClienteResponseDto;
import br.com.fiap.paymentapi.entity.Cliente;
import br.com.fiap.paymentapi.exception.ErrorMessage;
import br.com.fiap.paymentapi.jwt.JwtUserDetails;
import br.com.fiap.paymentapi.mapper.ClienteMapper;
import br.com.fiap.paymentapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Clientes", description = "Contém todas as opereções relativas ao recurso de um cliente")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;


    @Operation(summary = "Criar um novo cliente",
            description = "Recurso para criar um novo cliente vinculado a um usuário cadastrado. " +
                    "Requisição exige uso de um bearer token.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Cliente CPF já possui cadastro no sistema",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PostMapping
    public ResponseEntity<ClienteResponseDto> cadastrar(@Valid @RequestBody ClienteRequestDto clienteRequestDto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = clienteService.salvar(clienteMapper.toEntity(clienteRequestDto));
        return ResponseEntity.status(201).body(clienteMapper.toDto(cliente));
    }
}
