package com.productsAPI.controller;

import com.productsAPI.dto.LoginRequest;
import com.productsAPI.dto.UsuarioDTO;
import com.productsAPI.model.Usuario;
import com.productsAPI.service.UsuarioService;
import com.productsAPI.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário e retorna um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso e token gerado"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping(value = "/login", headers = "X-API-Version=v1")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
        );

        String token = jwtUtil.generateToken(loginRequest.getEmail());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário com nome, email e senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o usuário"),
            @ApiResponse(responseCode = "409", description = "Usuário já existe com os dados fornecidos")
    })
    @PostMapping(value = "/signup", headers = "X-API-Version=v1")
    public ResponseEntity<Usuario> signup(@Valid @RequestBody UsuarioDTO dto) {
        Usuario usuario = usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.ok(usuario);
    }
}
