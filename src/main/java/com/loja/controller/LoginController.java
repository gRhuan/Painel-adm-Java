package com.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import com.loja.service.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public String autenticar(@RequestParam String email, @RequestParam String senha, HttpSession session) {
        boolean autenticado = loginService.autenticar(email, senha);
        if (autenticado) {
            session.setAttribute("usuarioLogado", true); // Adiciona o estado de login na sessão
            return "redirect:/"; // Redireciona para a página inicial ou outra página de sucesso
        } else {
            session.setAttribute("usuarioLogado", false); // Reseta estado se necessário
            return "redirect:/login?error=true"; // Redireciona de volta para a página de login com mensagem de erro
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Limpa a sessão
        return "redirect:/"; // Redireciona para a página inicial após o logout
    }
}
