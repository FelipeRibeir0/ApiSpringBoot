package com.firstAPI.firstAPI.service;

import com.firstAPI.firstAPI.model.Produto;
import com.firstAPI.firstAPI.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setQuantidade(produtoAtualizado.getQuantidade());
            return produtoRepository.save(produto);
        }).orElse(null);
    }

    public Produto atualizarParcial(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id).map(produto -> {
            if (StringUtils.hasText(produtoAtualizado.getNome())) {
                produto.setNome(produtoAtualizado.getNome());
            }
            if (produtoAtualizado.getPreco() != null) {
                produto.setPreco(produtoAtualizado.getPreco());
            }
            if (produtoAtualizado.getQuantidade() != null) {
                produto.setQuantidade(produtoAtualizado.getQuantidade());
            }
            return produtoRepository.save(produto);
        }).orElse(null);
    }


    public boolean deletarProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
