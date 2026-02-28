package br.com.redesocial.controller;

import br.com.redesocial.dto.ArquivoMidiaDTO;
import br.com.redesocial.model.ArquivoMidia;
import br.com.redesocial.service.ArquivoMidiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arquivo-midia")
public class ArquivoMidiaController {
    @Autowired
    private ArquivoMidiaService arquivoMidiaService;

    @PostMapping("/create")
    public ResponseEntity<ArquivoMidiaDTO> adicionarArquivoMidia(@RequestBody ArquivoMidiaDTO arquivoMidia) {
        ArquivoMidia entidade = arquivoMidiaService.adicionarArquivo(arquivoMidia);

        ArquivoMidiaDTO resposta = new ArquivoMidiaDTO();
        resposta.setIdArquivo(arquivoMidia.getIdArquivo());
        resposta.setIdPublicacao(arquivoMidia.getIdPublicacao());
        resposta.setUrlMidia(arquivoMidia.getUrlMidia());

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/publicacao/{idPublicacao}")
    public ResponseEntity<List<ArquivoMidiaDTO>> listarArquivosPublicacao(@PathVariable Long idPublicacao){
        return ResponseEntity.ok(arquivoMidiaService.listarArquivosPorPublicacao(idPublicacao));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ArquivoMidiaDTO> buscarArquivoMidia(@PathVariable Long id){
        return ResponseEntity.ok(arquivoMidiaService.buscarArquivo(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarArquivoMidia(@PathVariable Long id){
        arquivoMidiaService.deletarArquivo(id);
        return ResponseEntity.noContent().build();
    }

}
