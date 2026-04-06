package com.example.marketplace.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.marketplace.model.CategoriaProduto;
import com.example.marketplace.model.Produto;
import com.example.marketplace.service.ServicoProduto;

/**
 * Testes unitários para o HomeController.
 * Utiliza MockMvc para simular requisições HTTP e Mockito para mockar
 * dependências.
 */
@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicoProduto servicoProduto;

    /**
     * Testa se a página inicial é carregada com sucesso e retorna a view correta
     */
    @Test
    void deveCarregarPaginaInicialComSucesso() throws Exception {
        // Arrange
        List<Produto> produtos = Arrays.asList(
                new Produto(1L, "Capinha Transparente", new BigDecimal("29.90"), CategoriaProduto.CAPINHA),
                new Produto(2L, "Película de Vidro", new BigDecimal("19.90"), CategoriaProduto.PELICULA));
        when(servicoProduto.listarTodos()).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", produtos));

        verify(servicoProduto, times(1)).listarTodos();
    }

    /**
     * Testa se a página inicial é carregada mesmo quando não há produtos
     */
    @Test
    void deveCarregarPaginaInicialSemProdutos() throws Exception {
        // Arrange
        when(servicoProduto.listarTodos()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", Collections.emptyList()));

        verify(servicoProduto, times(1)).listarTodos();
    }

    /**
     * Testa se o modelo contém os produtos corretos
     */
    @Test
    void deveAdicionarProdutosAoModelo() throws Exception {
        // Arrange
        Produto produto1 = new Produto(1L, "Carregador Rápido", new BigDecimal("49.90"), CategoriaProduto.CARREGADOR);
        Produto produto2 = new Produto(2L, "Fone de Ouvido", new BigDecimal("24.90"), CategoriaProduto.FONE);
        List<Produto> produtos = Arrays.asList(produto1, produto2);

        when(servicoProduto.listarTodos()).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", produtos));
    }
}
