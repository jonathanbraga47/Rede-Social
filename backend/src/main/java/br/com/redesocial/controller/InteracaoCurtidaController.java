package br.com.redesocial.controller;

import br.com.redesocial.model.InteracaoComentario;
import br.com.redesocial.model.InteracaoCurtida;
import br.com.redesocial.service.InteracaoCurtidaService;
import br.com.redesocial.dto.InteracaoCurtidaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curtida")
public class InteracaoCurtidaController {

    @Autowired
    private InteracaoCurtidaService interacaoCurtidaService;

    @PostMapping("/create")
    public ResponseEntity<InteracaoCurtida> createInteracaoCurtida(@Valid @RequestBody InteracaoCurtidaDTO interacaoCurtidaDTO){
        return ResponseEntity.ok(interacaoCurtidaService.createInteracaoCurtida(interacaoCurtidaDTO));
    }

    @GetMapping("/getAll")
    public List<InteracaoCurtida> getAllInteracaoCurtida(){
        return interacaoCurtidaService.getAllInteracaoCurtida();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<InteracaoCurtida> getInteracaoCurtida(@PathVariable Long id){
        return ResponseEntity.ok(interacaoCurtidaService.getInteracaoCurtida(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInteracaoCurtida(@PathVariable Long id){
        interacaoCurtidaService.deleteInteracaoCurtida(id);
        return ResponseEntity.noContent().build();
    }


}
