package br.com.fiap.paymentapi.controller;


import br.com.fiap.paymentapi.dto.UsuarioRequestDto;
import br.com.fiap.paymentapi.dto.UsuarioResponseDto;
import br.com.fiap.paymentapi.entity.Usuario;
import br.com.fiap.paymentapi.exception.ErrorMessage;
import br.com.fiap.paymentapi.mapper.UsuarioMapper;
import br.com.fiap.paymentapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        Usuario user = usuarioService.salvar(usuarioMapper.toUsuario(usuarioRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDto(user));
    }

}
