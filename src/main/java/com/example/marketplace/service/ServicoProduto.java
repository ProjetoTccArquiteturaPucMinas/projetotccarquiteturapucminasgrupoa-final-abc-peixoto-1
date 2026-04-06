package com.example.marketplace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.marketplace.model.Produto;
import com.example.marketplace.repository.ProdutoRepository;

@Service
public class ServicoProduto {

    private final ProdutoRepository repositorioProdutos;

    public ServicoProduto(ProdutoRepository repositorioProdutos) {
        this.repositorioProdutos = repositorioProdutos;
    }

    public List<Produto> listarTodos() {
        return repositorioProdutos.buscarTodos();
    }
}
