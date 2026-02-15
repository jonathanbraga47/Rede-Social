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
@RequestMapping("/comentarios")
public class InteracaoComentarioController {

    @Autowired
    private InteracaoComentarioService interacaoComentarioService;

    @PostMapping("/create")
    public ResponseEntity<InteracaoComentario> criarInteracaoComentario(@Valid @RequestBody InteracaoComentarioDTO interacaoComentarioDTO){
        InteracaoComentario savedComentario = interacaoComentarioService.criarInteracaoComentario(interacaoComentarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComentario);
    }

    @GetMapping("/getAll")
    public List<InteracaoComentario> listarInteracaoComentario(){
        return interacaoComentarioService.listarInteracocomentario();
    }

    @DeleteMapping("/get/{id}")
    public void deletarInteracaoComentario(@PathVariable Long id) {
        interacaoComentarioService.deletarInteracocomentario(id);
    }


}
