package com.example.marketplace.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.marketplace.model.Produto;
import com.example.marketplace.repository.ProdutoEmMemoriaRepository;

class ServicoProdutoTest {

    private ServicoProduto servicoProduto;

    @BeforeEach
    void configurar() {
        servicoProduto = new ServicoProduto(new ProdutoEmMemoriaRepository());
    }

    @Test
    void deveListarTodosOsProdutos() {
        // Act
        List<Produto> produtos = servicoProduto.listarTodos();

        // Assert
        assertNotNull(produtos);
        assertEquals(5, produtos.size());
    }

    @Test
    void deveRetornarProdutosComNomesCorretos() {
        // Act
        List<Produto> produtos = servicoProduto.listarTodos();

        // Assert
        assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Capinha Premium")));
        assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Carregador Turbo 30W")));
        assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Fone Bluetooth AirSound")));
        assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Película 3D")));
        assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Suporte Veicular Magnético")));
    }

    @Test
    void deveRetornarProdutosComPrecosCorretos() {
        // Act
        List<Produto> produtos = servicoProduto.listarTodos();

        // Assert
        Produto capinha = produtos.stream()
                .filter(p -> p.getNome().equals("Capinha Premium"))
                .findFirst()
                .orElse(null);

        assertNotNull(capinha);
        assertEquals(new BigDecimal("49.90"), capinha.getPreco());
    }
}
