package br.com.redesocial.controller;
import br.com.redesocial.dto.PerfilDTO;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.service.PerfilService;
import br.com.redesocial.model.Perfil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfil")
public class PerfilController {
    @Autowired
    private PerfilService perfilService;

    @PostMapping("/create")
    public ResponseEntity<Perfil> createPerfil(@Valid @RequestBody PerfilDTO perfilDTO){
        return ResponseEntity.ok(perfilService.createPerfil(perfilDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Perfil>> getAllPerfil(){
        return ResponseEntity.ok(perfilService.getAllPerfil());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Perfil> getPerfil(@PathVariable Long id){
        return ResponseEntity.ok(perfilService.getPerfil(id));
    }

    @GetMapping("get/{id}/publicacoes")
    public ResponseEntity<List<Publicacao>> getPerfilAllPublicacoes(@PathVariable Long id){
        return ResponseEntity.ok(perfilService.listarPublicacoesDoPerfil(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Perfil> updatePerfil(@PathVariable Long id, @RequestBody PerfilDTO perfilDTO){
        return ResponseEntity.ok(perfilService.updatePerfil(id, perfilDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id){
        perfilService.deletePerfil(id);
        return ResponseEntity.noContent().build();
    }

}