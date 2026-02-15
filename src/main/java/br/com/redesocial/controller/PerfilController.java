package br.com.redesocial.controller;
import br.com.redesocial.dto.PerfilDTO;
import br.com.redesocial.service.PerfilService;
import br.com.redesocial.model.Perfil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Perfil")
public class PerfilController {
    @Autowired
    private PerfilService perfilService;

    @PostMapping("/create")
    public ResponseEntity<Perfil> criarPerfil(@Valid @RequestBody PerfilDTO perfilDTO){
        Perfil savedPerfil = perfilService.createPerfil(perfilDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerfil);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Perfil>> listarPerfil(){
        return ResponseEntity.ok(perfilService.listarPerfil());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Perfil> buscarPerfil(@PathVariable Long id){
        return ResponseEntity.ok(perfilService.buscarPerfil(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Perfil> atualizarPerfil(@PathVariable Long id, @RequestBody PerfilDTO perfilDTO){
        return ResponseEntity.ok(perfilService.atualizarPerfil(id, perfilDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Perfil> deletarPerfil(@PathVariable Long id){
        perfilService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}