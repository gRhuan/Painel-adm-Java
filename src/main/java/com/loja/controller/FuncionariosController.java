package com.loja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loja.dto.RequisicaoNovoFuncionario;
import com.loja.model.Funcionarios;
import com.loja.service.FuncionarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/funcionarios")
public class FuncionariosController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/api")
    public List<Funcionarios> getAllFuncionarios() {
        return funcionarioService.findAll();
    }

    @GetMapping
    public ModelAndView index() {
        List<Funcionarios> funcionarios = funcionarioService.findAll();
        // Controla oque vai ser exibido
        ModelAndView mv = new ModelAndView("funcionarios/index");
        mv.addObject("funcionarios", funcionarios);
        return mv;
    }

    @PostMapping("/adicionar")
    public String criar(RequisicaoNovoFuncionario requisicao) {
        funcionarioService.save(requisicao);
        return "redirect:/funcionarios";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editarFuncionario(@PathVariable Long id, HttpSession session) {
        Boolean usuarioLogado = (Boolean) session.getAttribute("usuarioLogado");

        // Verifica se o usuário está logado
        if (usuarioLogado == null || !usuarioLogado) {
            return new ModelAndView("redirect:/"); // Redireciona para login
        }

        // Busca o funcionário e retorna a página de edição
        Funcionarios funcionario = funcionarioService.findById(id);
        ModelAndView mv = new ModelAndView("funcionarios/editar");
        mv.addObject("funcionario", funcionario);
        return mv;
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id, RequisicaoNovoFuncionario requisicao) {
        funcionarioService.updateById(id, requisicao);
        return "redirect:/funcionarios";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        funcionarioService.deleteById(id);
        return "redirect:/funcionarios";
    }

}
