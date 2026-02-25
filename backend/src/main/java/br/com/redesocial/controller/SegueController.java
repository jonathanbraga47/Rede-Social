package br.com.redesocial.controller;

import br.com.redesocial.dto.SegueDTO;
import br.com.redesocial.service.SegueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/segue")
public class SegueController {
    @Autowired
    private SegueService segueService;

    @PostMapping("/seguir/{idPerfilSeguidor}/{idPerfilSeguido}")
    public ResponseEntity<SegueDTO> seguirPerfil(@PathVariable Long idPerfilSeguidor, @PathVariable Long idPerfilSeguido) {
        return ResponseEntity.ok(segueService.seguirPerfil(idPerfilSeguidor, idPerfilSeguido));
    }

    //Lista seguidores
    @GetMapping("/seguidores/{perfilId}")
    public ResponseEntity<List<SegueDTO>> seguindo(@PathVariable Long perfilId) {
        return ResponseEntity.ok(segueService.listarSeguidores(perfilId));
    }

    //Lista de perfis seguidos
    @GetMapping("/seguindo/{perfilId}")
    public ResponseEntity<List<SegueDTO>> listarSeguidores(@PathVariable Long perfilId) {
        return ResponseEntity.ok(segueService.listarSeguindo(perfilId));
    }

    //se um perfil segue o outro
    @GetMapping("/verifica-segue/{idPerfilSeguidor}/{idPerfilSeguido}")
    public ResponseEntity<Boolean> verificaSeSeguePerfil(@PathVariable Long idPerfilSeguidor, @PathVariable Long idPerfilSeguido) {
        return ResponseEntity.ok(segueService.verificarSeSeguePerfil(idPerfilSeguidor, idPerfilSeguido));
    }

    //quantidade de seguidores de um perfil
    @GetMapping("/contar-seguidores/{perfilId}")
    public ResponseEntity<Long> contarSeguidores(@PathVariable Long perfilId) {
        return ResponseEntity.ok(segueService.contarSeguidores(perfilId));
    }

    @GetMapping("/contar-seguindo/{perfilId}")
    public ResponseEntity<Long> contarSeguindo(@PathVariable Long perfilId) {
        return ResponseEntity.ok(segueService.contarSeguindo(perfilId));
    }

    @DeleteMapping("/deixar-de-seguir/{idPerfilSeguidor}/{idPerfilSeguido}")
    public ResponseEntity<Void> deixarDeSeguir(@PathVariable Long idPerfilSeguidor, @PathVariable Long idPerfilSeguido) {
        segueService.deixarDeSeguir(idPerfilSeguidor, idPerfilSeguido);
        return ResponseEntity.noContent().build();
    }


}
