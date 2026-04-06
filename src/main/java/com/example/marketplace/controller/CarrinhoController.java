package com.example.marketplace.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.marketplace.model.ResumoCarrinho;
import com.example.marketplace.model.SelecaoCarrinho;
import com.example.marketplace.service.ServicoCarrinho;
import com.example.marketplace.service.ServicoProduto;

@Controller
public class CarrinhoController {

    private final ServicoCarrinho servicoCarrinho;
    private final ServicoProduto servicoProdutos;

    public CarrinhoController(ServicoCarrinho servicoCarrinho, ServicoProduto servicoProdutos) {
        this.servicoCarrinho = servicoCarrinho;
        this.servicoProdutos = servicoProdutos;
    }

    @PostMapping("/cart/summary")
    public String calcularResumo(
            @RequestParam(name = "productId", required = false) List<Long> idsProdutos,
            @RequestParam(name = "quantity", required = false) List<Integer> quantidades,
            Model modelo) {

        List<SelecaoCarrinho> selecoes = new ArrayList<>();

        if (idsProdutos != null && quantidades != null) {
            for (int i = 0; i < Math.min(idsProdutos.size(), quantidades.size()); i++) {
                Integer quantidade = quantidades.get(i);
                if (quantidade != null && quantidade > 0) {
                    selecoes.add(new SelecaoCarrinho(idsProdutos.get(i), quantidade));
                }
            }
        }

        ResumoCarrinho resumo = servicoCarrinho.construirResumo(selecoes);
        modelo.addAttribute("products", servicoProdutos.listarTodos());
        modelo.addAttribute("summary", resumo);
        modelo.addAttribute("featurePending", true);

        return "index";
    }
}
