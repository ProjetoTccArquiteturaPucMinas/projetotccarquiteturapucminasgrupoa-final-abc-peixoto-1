package com.example.marketplace.controller;

import static org.mockito.ArgumentMatchers.*;
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
import com.example.marketplace.model.ItemCarrinho;
import com.example.marketplace.model.Produto;
import com.example.marketplace.model.ResumoCarrinho;
import com.example.marketplace.service.ServicoCarrinho;
import com.example.marketplace.service.ServicoProduto;

/**
 * Testes unitários para o CarrinhoController.
 * Utiliza MockMvc para simular requisições HTTP e Mockito para mockar
 * dependências.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CarrinhoControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ServicoCarrinho servicoCarrinho;

        @MockBean
        private ServicoProduto servicoProduto;

        /**
         * Testa o cálculo do resumo do carrinho com produtos selecionados
         */
        @Test
        void deveCalcularResumoComProdutosSelecionados() throws Exception {
                // Arrange
                Produto produto = new Produto(1L, "Capinha", new BigDecimal("29.90"), CategoriaProduto.CAPINHA);
                List<Produto> produtos = Collections.singletonList(produto);

                ItemCarrinho item = new ItemCarrinho(produto, 2);
                ResumoCarrinho resumo = new ResumoCarrinho(
                                Collections.singletonList(item),
                                new BigDecimal("59.80"),
                                new BigDecimal("5.00"),
                                new BigDecimal("2.99"),
                                new BigDecimal("56.81"));

                when(servicoProduto.listarTodos()).thenReturn(produtos);
                when(servicoCarrinho.construirResumo(anyList())).thenReturn(resumo);

                // Act & Assert
                mockMvc.perform(post("/cart/summary")
                                .param("productId", "1")
                                .param("quantity", "2"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("index"))
                                .andExpect(model().attributeExists("products"))
                                .andExpect(model().attributeExists("summary"))
                                .andExpect(model().attributeExists("featurePending"))
                                .andExpect(model().attribute("summary", resumo))
                                .andExpect(model().attribute("featurePending", true));

                verify(servicoCarrinho, times(1)).construirResumo(anyList());
                verify(servicoProduto, times(1)).listarTodos();
        }

        /**
         * Testa o cálculo do resumo com múltiplos produtos
         */
        @Test
        void deveCalcularResumoComMultiplosProdutos() throws Exception {
                // Arrange
                Produto produto1 = new Produto(1L, "Capinha", new BigDecimal("29.90"), CategoriaProduto.CAPINHA);
                Produto produto2 = new Produto(2L, "Película", new BigDecimal("19.90"), CategoriaProduto.PELICULA);
                List<Produto> produtos = Arrays.asList(produto1, produto2);

                ItemCarrinho item1 = new ItemCarrinho(produto1, 1);
                ItemCarrinho item2 = new ItemCarrinho(produto2, 1);
                ResumoCarrinho resumo = new ResumoCarrinho(
                                Arrays.asList(item1, item2),
                                new BigDecimal("49.80"),
                                new BigDecimal("5.00"),
                                new BigDecimal("2.49"),
                                new BigDecimal("47.31"));

                when(servicoProduto.listarTodos()).thenReturn(produtos);
                when(servicoCarrinho.construirResumo(anyList())).thenReturn(resumo);

                // Act & Assert
                mockMvc.perform(post("/cart/summary")
                                .param("productId", "1", "2")
                                .param("quantity", "1", "1"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("index"))
                                .andExpect(model().attributeExists("summary"))
                                .andExpect(model().attribute("summary", resumo));

                verify(servicoCarrinho, times(1)).construirResumo(anyList());
                verify(servicoProduto, times(1)).listarTodos();
        }

        /**
         * Testa o comportamento quando nenhum produto é selecionado
         */
        @Test
        void deveCalcularResumoSemProdutosSelecionados() throws Exception {
                // Arrange
                List<Produto> produtos = Collections.emptyList();
                ResumoCarrinho resumoVazio = new ResumoCarrinho(
                                Collections.emptyList(),
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO);

                when(servicoProduto.listarTodos()).thenReturn(produtos);
                when(servicoCarrinho.construirResumo(anyList())).thenReturn(resumoVazio);

                // Act & Assert
                mockMvc.perform(post("/cart/summary"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("index"))
                                .andExpect(model().attributeExists("summary"))
                                .andExpect(model().attribute("summary", resumoVazio));

                verify(servicoCarrinho, times(1)).construirResumo(Collections.emptyList());
                verify(servicoProduto, times(1)).listarTodos();
        }

        /**
         * Testa o comportamento quando quantidade é zero ou negativa (deve ser
         * ignorada)
         */
        @Test
        void deveIgnorarProdutosComQuantidadeInvalida() throws Exception {
                // Arrange
                List<Produto> produtos = Collections.emptyList();
                ResumoCarrinho resumo = new ResumoCarrinho(
                                Collections.emptyList(),
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO);

                when(servicoProduto.listarTodos()).thenReturn(produtos);
                when(servicoCarrinho.construirResumo(anyList())).thenReturn(resumo);

                // Act & Assert - quantidade zero
                mockMvc.perform(post("/cart/summary")
                                .param("productId", "1")
                                .param("quantity", "0"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("index"));

                // Act & Assert - quantidade negativa
                mockMvc.perform(post("/cart/summary")
                                .param("productId", "1")
                                .param("quantity", "-1"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("index"));

                verify(servicoCarrinho, times(2)).construirResumo(Collections.emptyList());
        }

        /**
         * Testa o comportamento quando os arrays de productId e quantity têm tamanhos
         * diferentes
         */
        @Test
        void deveProcessarCorretamenteArraysComTamanhosDiferentes() throws Exception {
                // Arrange
                Produto produto = new Produto(1L, "Capinha", new BigDecimal("29.90"), CategoriaProduto.CAPINHA);
                List<Produto> produtos = Collections.singletonList(produto);

                ResumoCarrinho resumo = new ResumoCarrinho(
                                Collections.emptyList(),
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO);

                when(servicoProduto.listarTodos()).thenReturn(produtos);
                when(servicoCarrinho.construirResumo(anyList())).thenReturn(resumo);

                // Act & Assert
                mockMvc.perform(post("/cart/summary")
                                .param("productId", "1", "2", "3")
                                .param("quantity", "1"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("index"));

                verify(servicoCarrinho, times(1)).construirResumo(anyList());
        }
}
