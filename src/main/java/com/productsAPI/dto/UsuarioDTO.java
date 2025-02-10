package com.productsAPI.dto;

import com.productsAPI.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private Usuario.Role role;
}