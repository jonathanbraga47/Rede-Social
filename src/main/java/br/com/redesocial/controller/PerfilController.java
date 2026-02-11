package br.com.redesocial.controller;
import br.com.redesocial.dto.PerfilDTO;
import br.com.redesocial.service.PerfilService;
import br.com.redesocial.model.Perfil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



}