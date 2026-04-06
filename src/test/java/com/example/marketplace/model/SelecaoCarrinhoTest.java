package com.example.marketplace.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SelecaoCarrinhoTest {

    @Test
    void deveCriarSelecaoCarrinhoComConstrutorVazio() {
        // Act
        SelecaoCarrinho selecao = new SelecaoCarrinho();

        // Assert
        assertNotNull(selecao);
        assertNull(selecao.getProdutoId());
        assertNull(selecao.getQuantidade());
    }

    @Test
    void deveCriarSelecaoCarrinhoComConstrutorCompleto() {
        // Act
        SelecaoCarrinho selecao = new SelecaoCarrinho(1L, 5);

        // Assert
        assertNotNull(selecao);
        assertEquals(1L, selecao.getProdutoId());
        assertEquals(5, selecao.getQuantidade());
    }

    @Test
    void deveDefinirProdutoId() {
        // Arrange
        SelecaoCarrinho selecao = new SelecaoCarrinho();

        // Act
        selecao.setProdutoId(10L);

        // Assert
        assertEquals(10L, selecao.getProdutoId());
    }

    @Test
    void deveDefinirQuantidade() {
        // Arrange
        SelecaoCarrinho selecao = new SelecaoCarrinho();

        // Act
        selecao.setQuantidade(3);

        // Assert
        assertEquals(3, selecao.getQuantidade());
    }

    @Test
    void devePermitirAlterarProdutoIdEQuantidade() {
        // Arrange
        SelecaoCarrinho selecao = new SelecaoCarrinho(1L, 2);

        // Act
        selecao.setProdutoId(5L);
        selecao.setQuantidade(10);

        // Assert
        assertEquals(5L, selecao.getProdutoId());
        assertEquals(10, selecao.getQuantidade());
    }
}
