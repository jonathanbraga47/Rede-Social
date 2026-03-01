package br.com.redesocial.controller;

import br.com.redesocial.model.ViewRankingUsuarios;
import br.com.redesocial.repository.ViewRankingUsuariosRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/viewRankingUsuarios")
public class ViewRankingUsuariosController {

    private final ViewRankingUsuariosRepository repository;

    public ViewRankingUsuariosController(ViewRankingUsuariosRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/ranking")
    public List<ViewRankingUsuarios> getRankingUsuarios() {
        return repository.findAll();
    }
}