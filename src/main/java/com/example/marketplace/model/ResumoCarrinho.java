package com.example.marketplace.model;

import java.math.BigDecimal;
import java.util.List;

public class ResumoCarrinho {

    private List<ItemCarrinho> itens;
    private BigDecimal subtotal;
    private BigDecimal percentualDesconto;
    private BigDecimal valorDesconto;
    private BigDecimal total;

    public ResumoCarrinho(List<ItemCarrinho> itens, BigDecimal subtotal, BigDecimal percentualDesconto,
            BigDecimal valorDesconto, BigDecimal total) {
        this.itens = itens;
        this.subtotal = subtotal;
        this.percentualDesconto = percentualDesconto;
        this.valorDesconto = valorDesconto;
        this.total = total;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getPercentualDesconto() {
        return percentualDesconto;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
