package com.productsAPI.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    @NotBlank(message = "O nome do produto não pode estar vazio")
    private String nome;

    @NotNull(message = "A quantidade do produto não pode ser nula")
    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "O preço do produto não pode ser nulo")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;
}
