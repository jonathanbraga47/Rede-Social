package br.com.redesocial.controller;

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
@RequestMapping("/curtidas")
public class InteracaoCurtidaController {

    @Autowired
    private InteracaoCurtidaService interacaoCurtidaService;

    @PostMapping("/create")
    public ResponseEntity<InteracaoCurtida> criarInteracaoCurtida(@Valid @RequestBody InteracaoCurtidaDTO interacaoCurtidaDTO){
        InteracaoCurtida savedCurtida = interacaoCurtidaService.criarInteracaoCurtida(interacaoCurtidaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCurtida);
    }

    @GetMapping("/getAll")
    public List<InteracaoCurtida> listarInteracaoCurtida(){
        return interacaoCurtidaService.listarInteracaoCurtida();
    }

    @DeleteMapping("/get/{id}")
    public void deletarInteracaoCurtida(@PathVariable Long id){
        interacaoCurtidaService.deletarInteracaoCurtida(id);
    }
}
