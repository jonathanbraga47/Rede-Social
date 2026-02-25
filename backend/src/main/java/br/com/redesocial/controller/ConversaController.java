package br.com.redesocial.controller;

import br.com.redesocial.dto.ConversaDTO;
import br.com.redesocial.dto.ConversaRequestDTO;
import br.com.redesocial.dto.ParticipaDTO;
import br.com.redesocial.service.ConversaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversa")
public class ConversaController {
    @Autowired
    private ConversaService conversaService;

    @PostMapping("/create/{idPergilSeguidor}/{idPerfilSeguido}")
    public ResponseEntity<ConversaDTO> criarConversa(@PathVariable Long idPergilSeguidor, @PathVariable Long idPerfilSeguido) {
        return ResponseEntity.ok(conversaService.criarConversa(idPergilSeguidor, idPerfilSeguido));
    }

    @GetMapping("/get/{idConversa}")
    public ResponseEntity<ConversaDTO> buscarConversa(@PathVariable Long idConversa) {
        return ResponseEntity.ok(conversaService.buscarConversa(idConversa));
    }

    @GetMapping("/participantes/{idConversa}")
    public ResponseEntity<List<ParticipaDTO>> listarParticipantes(@PathVariable Long idConversa) {
        return ResponseEntity.ok(conversaService.listarParticipasDoConversa(idConversa));
    }

    @GetMapping("/verifica-participacao/{idPerfil}/{idConversa}")
    public ResponseEntity<Boolean> verificaParticipacao(@PathVariable Long idPerfil, @PathVariable Long idConversa) {
        return ResponseEntity.ok(conversaService.verificarParticipacao(idPerfil, idConversa));
    }

    @DeleteMapping("/delete/{idConversa}")
    public ResponseEntity<Void> deletarConversa(@PathVariable Long idConversa) {
        conversaService.deletarConversa(idConversa);
        return ResponseEntity.noContent().build();
    }
}
