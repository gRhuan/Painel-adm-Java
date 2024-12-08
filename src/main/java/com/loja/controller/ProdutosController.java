package com.loja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.loja.dto.RequisicaoNovoProduto;
import com.loja.model.Produtos;
import com.loja.service.ProdutoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/produtos") // Agrupando rotas relacionadas a produtos
public class ProdutosController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/api")
    @ResponseBody
    public List<Produtos> getAllProdutos() {
        return produtoService.findAll();
    }

    @GetMapping
    public ModelAndView index() {
        List<Produtos> produtos = produtoService.findAll();
        ModelAndView mv = new ModelAndView("produtos/index");
        mv.addObject("produtos", produtos);
        return mv;
    }

    @PostMapping("/adicionar")
    public String criar(RequisicaoNovoProduto requisicao) {
        produtoService.save(requisicao);
        return "redirect:/produtos";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id, HttpSession session) {
        // Verifica se o usuário está logado
        Boolean usuarioLogado = (Boolean) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || !usuarioLogado) {
            // Redireciona para a página de login se o usuário não estiver logado
            return new ModelAndView("redirect:/");
        }

        // Se estiver logado, carrega o produto
        Produtos produto = produtoService.findById(id);
        ModelAndView mv = new ModelAndView("produtos/editar");
        mv.addObject("produto", produto);
        return mv;
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id, RequisicaoNovoProduto requisicao) {
        produtoService.updateById(id, requisicao);
        return "redirect:/produtos";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        produtoService.deleteById(id);
        return "redirect:/produtos";
    }
}
