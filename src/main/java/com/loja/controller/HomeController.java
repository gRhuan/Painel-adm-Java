package com.loja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            session.setAttribute("usuarioLogado", false); // Valor padrão para não logado
        }
        return "home/index"; // Substitua pelo caminho correto do template
    }

    @RequestMapping("/sobre")
    public String sobre() {
        return "home/sobre";
    }

}
