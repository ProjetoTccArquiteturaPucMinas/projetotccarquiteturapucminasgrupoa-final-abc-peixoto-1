package com.example.marketplace.model;

import java.math.BigDecimal;

public class Produto {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private CategoriaProduto categoria;

    public Produto(Long id, String nome, BigDecimal preco, CategoriaProduto categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }
}
