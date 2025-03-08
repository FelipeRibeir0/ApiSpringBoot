package com.productsAPI.controller;

import com.productsAPI.dto.ProdutoDTO;
import com.productsAPI.model.Produto;
import com.productsAPI.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProdutoController {

    @Autowired
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Criar um novo produto", description = "Cria um novo produto com nome, quantidade e preço. Retorna o produto criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o produto"),
            @ApiResponse(responseCode = "409", description = "Produto já existe com os dados fornecidos")
    })
    @PostMapping(headers = "X-API-Version=v1")
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody ProdutoDTO dto) {
        Produto produtoSalvo = produtoService.salvarProduto(dto);
        return ResponseEntity.status(201).body(produtoSalvo);
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado")
    })
    @GetMapping(headers = "X-API-Version=v1")
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarTodos();
        if (produtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Busca um produto pelo ID", description = "Retorna um produto específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado com o ID fornecido")
    })
    @GetMapping(value = "/{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Produto> listarProdutosPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        return produto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza todos os atributos de um produto", description = "Substitui os atributos de um produto existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping(value = "/{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Produto> editarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto) {
        Produto produtoEditado = produtoService.atualizarProduto(id, dto);
        if (produtoEditado != null) {
            return ResponseEntity.ok(produtoEditado);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualiza parcialmente um produto", description = "Atualiza apenas alguns atributos de um produto específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto parcialmente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping(value = "/{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Produto> atualizarProdutoParcial(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto) {
        Produto produto = produtoService.atualizarParcial(id, dto);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deleta um produto", description = "Exclui o produto especificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping(value = "/{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        boolean deletado = produtoService.deletarProduto(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
