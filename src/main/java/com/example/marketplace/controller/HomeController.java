package com.example.marketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.marketplace.service.ServicoProduto;

@Controller
public class HomeController {

    private final ServicoProduto servicoProdutos;

    public HomeController(ServicoProduto servicoProdutos) {
        this.servicoProdutos = servicoProdutos;
    }

    @GetMapping("/")
    public String index(Model modelo) {
        modelo.addAttribute("products", servicoProdutos.listarTodos());
        return "index";
    }
}
