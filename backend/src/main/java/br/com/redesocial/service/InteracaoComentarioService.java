package br.com.redesocial.service;

import br.com.redesocial.dto.InteracaoComentarioDTO;
import br.com.redesocial.model.*;
import br.com.redesocial.repository.*;
import br.com.redesocial.service.InteracaoComentarioService;
import br.com.redesocial.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InteracaoComentarioService {

    @Autowired
    private InteracaoComentarioRepository interacaoComentarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private PublicacaoRepository publicacaoRepository;


    public InteracaoComentario createInteracaoComentario(InteracaoComentarioDTO dto) {
        Perfil perfil = perfilRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Publicacao publicacao = publicacaoRepository.findById(dto.getPublicacaoId())
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        InteracaoComentario comentario = new InteracaoComentario();
        comentario.setPerfil(perfil);       // Herdado de Interacao
        comentario.setPublicacao(publicacao); // Herdado de Interacao
        comentario.setDataHora(LocalDateTime.now());
        comentario.setTexto(dto.getConteudo()); // Específico de InteracaoComentario

        return interacaoComentarioRepository.save(comentario);
    }

    public List<InteracaoComentario> getAllInteracocomentario(){
        return interacaoComentarioRepository.findAll();
    }

    public InteracaoComentario getInteracaoComentario(Long id){
        return interacaoComentarioRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Comentário não encontrado"));
    }
    public List<InteracaoComentario> getByPerfil(Long idPerfil) {
    return interacaoComentarioRepository.findByPerfilId(idPerfil);
}

    public void deleteInteracaoComentario(Long id){
        interacaoComentarioRepository.deleteById(id);
    }
}
