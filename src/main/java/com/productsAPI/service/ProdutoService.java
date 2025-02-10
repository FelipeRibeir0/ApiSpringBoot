package com.productsAPI.service;

import com.productsAPI.model.Produto;
import com.productsAPI.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import com.productsAPI.dto.ProdutoDTO;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto salvarProduto(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setQuantidade(dto.getQuantidade());
        produto.setPreco(dto.getPreco());
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

    public Produto atualizarProduto(Long id, ProdutoDTO dto) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            produto.setNome(dto.getNome());
            produto.setQuantidade(dto.getQuantidade());
            produto.setPreco(dto.getPreco());
            return produtoRepository.save(produto);
        }
        return null;
    }

    public Produto atualizarParcial(Long id, ProdutoDTO dto) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            if (dto.getNome() != null) produto.setNome(dto.getNome());
            if (dto.getQuantidade() != null) produto.setQuantidade(dto.getQuantidade());
            if (dto.getPreco() != null) produto.setPreco(dto.getPreco());
            return produtoRepository.save(produto);
        }
        return null;
    }


    public boolean deletarProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
