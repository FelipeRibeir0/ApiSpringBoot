package com.firstAPI.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(description = "Modelo que representa um produto.")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Schema(description = "ID único do produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Camiseta")
    private String nome;

    @Schema(description = "Preço do produto", example = "29.99")
    private Double preco;

    @Schema(description = "Quantidade do produto existente no estoque", example = "39")
    private Integer quantidade;
    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}