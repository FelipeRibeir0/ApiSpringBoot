package com.productsAPI.controller;

import com.productsAPI.dto.UsuarioDTO;
import com.productsAPI.model.Usuario;
import com.productsAPI.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário com nome, email e senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o usuário"),
            @ApiResponse(responseCode = "409", description = "Usuário já existe com os dados fornecidos")
    })
    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody UsuarioDTO dto) {
        Usuario usuario = usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.ok(usuario);
    }
}
