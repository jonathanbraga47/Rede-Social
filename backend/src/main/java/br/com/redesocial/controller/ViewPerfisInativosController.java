package br.com.redesocial.controller;

import br.com.redesocial.model.ViewPerfisInativos;
import br.com.redesocial.repository.ViewPerfisInativosRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/viewPerfisInativos")
public class ViewPerfisInativosController {

    private final ViewPerfisInativosRepository repository;


    public ViewPerfisInativosController(ViewPerfisInativosRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/inatividade")
    public List<ViewPerfisInativos> getPerfisInatividade() {
        return repository.findAll();
    }
}
