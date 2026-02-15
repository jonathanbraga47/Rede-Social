package br.com.redesocial.controller;

import br.com.redesocial.dto.PublicacaoDTO;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Publicacao")
public class PublicacaoController {
    @Autowired
    private PublicacaoService publicacaoService;

    @PostMapping("/Create")
    public ResponseEntity<Publicacao> criarPublicacao(@RequestBody PublicacaoDTO publicacaoDTO){
        return ResponseEntity.ok(publicacaoService.criarPublicacao(publicacaoDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Publicacao>> listarPublicacoes() {
        return ResponseEntity.ok(publicacaoService.listarPublicacao());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Publicacao> buscarPublicacao(@PathVariable Long id) {
        return ResponseEntity.ok(publicacaoService.buscarPublicacao(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Publicacao> atualizarPublicacao(@PathVariable Long id, @RequestBody PublicacaoDTO publicacaoDTO) {
        return ResponseEntity.ok(publicacaoService.atualizarPublicacao(id, publicacaoDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Publicacao> deletarPublicacao(@PathVariable Long id) {
        publicacaoService.deletarPublicacao(id);
        return ResponseEntity.noContent().build();
    }

}
