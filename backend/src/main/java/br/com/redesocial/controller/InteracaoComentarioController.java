package br.com.redesocial.controller;

import br.com.redesocial.model.InteracaoComentario;
import br.com.redesocial.model.InteracaoCurtida;
import br.com.redesocial.service.InteracaoComentarioService;
import br.com.redesocial.dto.InteracaoComentarioDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentario")
public class InteracaoComentarioController {

    @Autowired
    private InteracaoComentarioService interacaoComentarioService;

    @PostMapping("/create")
    public ResponseEntity<InteracaoComentario> createInteracaoComentario(@Valid @RequestBody InteracaoComentarioDTO interacaoComentarioDTO){
        return ResponseEntity.ok(interacaoComentarioService.createInteracaoComentario(interacaoComentarioDTO));
    }

    @GetMapping("/getAll")
    public List<InteracaoComentario> getAllInteracaoComentario(){
        return interacaoComentarioService.getAllInteracocomentario();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<InteracaoComentario> getInteracaoComentario(@PathVariable Long id) {
        return ResponseEntity.ok(interacaoComentarioService.getInteracaoComentario(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInteracaoComentario(@PathVariable Long id){
        interacaoComentarioService.deleteInteracaoComentario(id);
        return ResponseEntity.noContent().build();
    }
}
