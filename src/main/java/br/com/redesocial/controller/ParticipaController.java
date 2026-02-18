package br.com.redesocial.controller;

import br.com.redesocial.dto.ConversaDTO;
import br.com.redesocial.dto.ParticipaDTO;
import br.com.redesocial.service.ParticipaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participa")
public class ParticipaController {
    @Autowired
    private ParticipaService participaService;

    @PostMapping("/create/{idPerfil1}/{idPerfil2}")
    public ResponseEntity<ConversaDTO> adicionarParticipante(@PathVariable Long idPerfilSeguidor, @PathVariable Long idPerfilSeguido) {
        return ResponseEntity.ok(participaService.criarConversa(idPerfilSeguidor, idPerfilSeguido));
    }

    @GetMapping("/getAll/{idPerfil}")
    public ResponseEntity<List<ConversaDTO>> listarConversasDoPerfil(@PathVariable Long idPerfil) {
        return ResponseEntity.ok(participaService.listarConversasDePerfil(idPerfil));
    }

    @GetMapping("/getConversa/{idConversa}")
    public ResponseEntity<ConversaDTO> buscarConversaPorId(@PathVariable Long idConversa) {
        return ResponseEntity.ok(participaService.buscarConversa(idConversa));
    }

    @GetMapping("/get/{idPerfil}/{idConversa}")
    public ResponseEntity<Boolean> verificaParticipacao(@PathVariable Long idPerfil, @PathVariable Long idConversa) {
        return ResponseEntity.ok(participaService.verificaParticipacao(idPerfil, idConversa));
    }

    @DeleteMapping("removeParticipante/{idPerfil}/{idConversa}")
    public ResponseEntity<Void> removerParticipante(@PathVariable Long idPerfil, @PathVariable Long idConversa) {
        participaService.removerParticipante(idPerfil, idConversa);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{idConversa}")
    public ResponseEntity<Void> removerParticipante(@PathVariable Long idConversa) {
        participaService.deletarCOnversa(idConversa);
        return ResponseEntity.noContent().build();
    }
}
