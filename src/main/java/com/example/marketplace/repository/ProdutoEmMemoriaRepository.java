package com.example.marketplace.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.marketplace.model.CategoriaProduto;
import com.example.marketplace.model.Produto;

@Repository
public class ProdutoEmMemoriaRepository implements ProdutoRepository {

    private final List<Produto> produtos = new ArrayList<>();

    public ProdutoEmMemoriaRepository() {
        produtos.add(new Produto(1L, "Capinha Premium", new BigDecimal("49.90"), CategoriaProduto.CAPINHA));
        produtos.add(new Produto(2L, "Carregador Turbo 30W", new BigDecimal("119.90"), CategoriaProduto.CARREGADOR));
        produtos.add(new Produto(3L, "Fone Bluetooth AirSound", new BigDecimal("199.90"), CategoriaProduto.FONE));
        produtos.add(new Produto(4L, "Película 3D", new BigDecimal("29.90"), CategoriaProduto.PELICULA));
        produtos.add(new Produto(5L, "Suporte Veicular Magnético", new BigDecimal("59.90"), CategoriaProduto.SUPORTE));
    }

    @Override
    public List<Produto> buscarTodos() {
        return produtos;
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtos.stream().filter(produto -> produto.getId().equals(id)).findFirst();
    }
}
