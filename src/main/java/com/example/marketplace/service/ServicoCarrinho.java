package com.example.marketplace.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.marketplace.model.CategoriaProduto;
import com.example.marketplace.model.ItemCarrinho;
import com.example.marketplace.model.Produto;
import com.example.marketplace.model.ResumoCarrinho;
import com.example.marketplace.model.SelecaoCarrinho;
import com.example.marketplace.repository.ProdutoRepository;

@Service
public class ServicoCarrinho {

    private final ProdutoRepository repositorioProdutos;

    public ServicoCarrinho(ProdutoRepository repositorioProdutos) {
        this.repositorioProdutos = repositorioProdutos;
    }

    public ResumoCarrinho construirResumo(List<SelecaoCarrinho> selecoes) {

        List<ItemCarrinho> itens = new ArrayList<>();

        for (SelecaoCarrinho selecao : selecoes) {
            Produto produto = repositorioProdutos.buscarPorId(selecao.getProdutoId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Produto não encontrado: " + selecao.getProdutoId()));
            itens.add(new ItemCarrinho(produto, selecao.getQuantidade()));
        }

        BigDecimal subtotal = itens.stream()
                .map(ItemCarrinho::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal percentualDesconto = calcularPercentualDesconto(itens);
        BigDecimal valorDesconto = subtotal.multiply(percentualDesconto).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        BigDecimal total = subtotal.subtract(valorDesconto);

        return new ResumoCarrinho(itens, subtotal, percentualDesconto, valorDesconto, total);
    }


    private BigDecimal calcularPercentualDesconto(List<ItemCarrinho> itens) {
        BigDecimal desconto = BigDecimal.ZERO;

        int totalItens = itens.stream().mapToInt(ItemCarrinho::getQuantidade).sum();
        desconto = desconto.add(obterDescontoQuantidade(totalItens));

        for (ItemCarrinho item : itens) {
            BigDecimal descontoCategoria = obterDescontoCategoria(item.getProduto().getCategoria());
            desconto = desconto.add(descontoCategoria);
        }

        return desconto;
    }

    private BigDecimal obterDescontoQuantidade(int totalItens) {
        if (totalItens >= 4) {
            return BigDecimal.valueOf(10);
        } else if (totalItens == 3) {
            return BigDecimal.valueOf(7);
        } else if (totalItens == 2) {
            return BigDecimal.valueOf(5);
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal obterDescontoCategoria(CategoriaProduto categoria) {
        if (categoria == null) {
            return BigDecimal.ZERO;
        }

        return switch (categoria) {
            case CAPINHA -> BigDecimal.valueOf(3);
            case CARREGADOR -> BigDecimal.valueOf(5);
            case FONE -> BigDecimal.valueOf(3);
            case PELICULA -> BigDecimal.valueOf(2);
            case SUPORTE -> BigDecimal.valueOf(2);
        };
    }
}
