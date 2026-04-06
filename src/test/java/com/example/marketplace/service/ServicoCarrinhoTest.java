package com.example.marketplace.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.marketplace.model.ResumoCarrinho;
import com.example.marketplace.model.SelecaoCarrinho;
import com.example.marketplace.repository.ProdutoEmMemoriaRepository;

class ServicoCarrinhoTest {

    private ServicoCarrinho servicoCarrinho;

    @BeforeEach
    void configurar() {
        servicoCarrinho = new ServicoCarrinho(new ProdutoEmMemoriaRepository());
    }

    @Test
    void deveCalcularSubtotalSemDescontoQuandoApenasUmProdutoForSelecionado() {
        List<SelecaoCarrinho> selecoes = List.of(
                new SelecaoCarrinho(4L, 1));

        ResumoCarrinho resumo = servicoCarrinho.construirResumo(selecoes);

        assertEquals(new BigDecimal("29.90"), resumo.getSubtotal().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("2.00"), resumo.getPercentualDesconto().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("29.30"), resumo.getTotal().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void deveAplicarDescontoDeQuantidadeQuandoDoisProdutosForemSelecionados() {
        List<SelecaoCarrinho> selecoes = List.of(
                new SelecaoCarrinho(4L, 1),
                new SelecaoCarrinho(5L, 1));

        ResumoCarrinho resumo = servicoCarrinho.construirResumo(selecoes);

        assertEquals(new BigDecimal("89.80"), resumo.getSubtotal().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("9.00"), resumo.getPercentualDesconto().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("81.72"), resumo.getTotal().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void deveAplicarDescontoDeCategoriaEQuantidadeRespeitandoLimiteMaximo() {
        List<SelecaoCarrinho> selecoes = List.of(
                new SelecaoCarrinho(2L, 1),
                new SelecaoCarrinho(3L, 1),
                new SelecaoCarrinho(1L, 1),
                new SelecaoCarrinho(4L, 1));

        ResumoCarrinho resumo = servicoCarrinho.construirResumo(selecoes);

        assertEquals(new BigDecimal("399.60"), resumo.getSubtotal().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("23.00"), resumo.getPercentualDesconto().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("307.69"), resumo.getTotal().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void deveAplicarDescontoDeCategoriaParaCapinhaMesmoSemDescontoDeQuantidade() {
        List<SelecaoCarrinho> selecoes = List.of(
                new SelecaoCarrinho(1L, 1));

        ResumoCarrinho resumo = servicoCarrinho.construirResumo(selecoes);

        assertEquals(new BigDecimal("49.90"), resumo.getSubtotal().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("3.00"), resumo.getPercentualDesconto().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("48.40"), resumo.getTotal().setScale(2, RoundingMode.HALF_UP));
    }
}
