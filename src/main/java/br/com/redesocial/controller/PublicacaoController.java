package br.com.redesocial.controller;

import br.com.redesocial.dto.PublicacaoDTO;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicacao")
public class PublicacaoController {
    @Autowired
    private PublicacaoService publicacaoService;

    @PostMapping("/create")
    public ResponseEntity<Publicacao> createPublicacao(@RequestBody PublicacaoDTO publicacaoDTO){
        return ResponseEntity.ok(publicacaoService.createPublicacao(publicacaoDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PublicacaoDTO>> getAllPublicacoes() {
        return ResponseEntity.ok(publicacaoService.getAllPublicacao());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PublicacaoDTO> getPublicacao(@PathVariable Long id) {
        return ResponseEntity.ok(publicacaoService.getPublicacao(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PublicacaoDTO> updatePublicacao(@PathVariable Long id, @RequestBody PublicacaoDTO publicacaoDTO) {
        return ResponseEntity.ok(publicacaoService.updatePublicacao(id, publicacaoDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePublicacao(@PathVariable Long id) {
        publicacaoService.deletePublicacao(id);
        return ResponseEntity.noContent().build();
    }
}