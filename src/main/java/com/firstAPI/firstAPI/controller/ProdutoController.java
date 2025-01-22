package com.firstAPI.firstAPI.controller;

import com.firstAPI.firstAPI.model.Produto;
import com.firstAPI.firstAPI.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Criar produto
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvarProduto(produto);
        return ResponseEntity.ok(produtoSalvo);
    }

    // Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    // Buscar produtos pelo nome
    @GetMapping("/nome")
    public ResponseEntity<List<Produto>> listarProdutosPorNome(@RequestParam String nome) {
        List<Produto> produtos = produtoService.buscarPorNome(nome);
        if (produtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtos);
    }

    // Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> listarProdutosPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Editar produto
    @PutMapping("/{id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto produtoEditado = produtoService.atualizarProduto(id, produtoAtualizado);
        if (produtoEditado != null) {
            return ResponseEntity.ok(produtoEditado);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Produto> atualizarProdutoParcial(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto produto = produtoService.atualizarParcial(id, produtoAtualizado);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Excluir produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        boolean deletado = produtoService.deletarProduto(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
