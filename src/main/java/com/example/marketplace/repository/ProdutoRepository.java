package com.example.marketplace.repository;

import java.util.List;
import java.util.Optional;

import com.example.marketplace.model.Produto;

public interface ProdutoRepository {
    List<Produto> buscarTodos();

    Optional<Produto> buscarPorId(Long id);
}
