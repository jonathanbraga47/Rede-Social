package br.com.redesocial.controller;

import br.com.redesocial.model.ViewEngajamento;
import br.com.redesocial.repository.ViewEngajamentoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
@RequestMapping("/viewEngajamento")
public class ViewEngajamentoController {
    private final ViewEngajamentoRepository repository;

    public ViewEngajamentoController(ViewEngajamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/engajamento")
    public List<ViewEngajamento> getEngajamento() {
        return repository.findAll();
    }
}
