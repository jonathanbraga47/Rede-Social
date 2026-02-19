package br.com.redesocial.controller;

import br.com.redesocial.dto.MensagemDTO;
import br.com.redesocial.dto.MensagemRequestDTO;
import br.com.redesocial.service.MensagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {
    @Autowired
    private MensagemService mensagemService;

    @PostMapping("/send")
    public ResponseEntity<MensagemDTO> enviarMensagem(@Valid @RequestBody MensagemRequestDTO mensagemRequestDTO){
        return ResponseEntity.ok(mensagemService.enviarMensagem(mensagemRequestDTO));
    }

    @GetMapping("/getAll/{idCOnversa}")
    public ResponseEntity<List<MensagemDTO>> getAll(@PathVariable Long idCOnversa){
        return ResponseEntity.ok(mensagemService.listarMensagensDaConversa(idCOnversa));
    }

    @GetMapping("get/{idMensagem}")
    public ResponseEntity<MensagemDTO> getById(@PathVariable Long idMensagem){
        return ResponseEntity.ok(mensagemService.buscarMensagem(idMensagem));
    }

    @DeleteMapping("/delete/{idMensagem}")
    public ResponseEntity<Void> deletarMensagem(@PathVariable Long idMensagem){
        mensagemService.deletarMensagem(idMensagem);
        return ResponseEntity.noContent().build();
    }
}
