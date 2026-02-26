package br.com.redesocial.controller;

import br.com.redesocial.model.ViewHistoricoMensagens;
import br.com.redesocial.repository.ViewHistoricoMensagensRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/historicoMensagem")
public class ViewHistoricoMensagemController {
    private final ViewHistoricoMensagensRepository repository;

    public ViewHistoricoMensagemController(ViewHistoricoMensagensRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/historico/{id}")
    public List<ViewHistoricoMensagens> getHistoricoMensagens(@PathVariable Long id) {
        return repository.findByIdConversa(id);
    }
}
