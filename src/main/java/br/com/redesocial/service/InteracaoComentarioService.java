package br.com.redesocial.service;

import br.com.redesocial.dto.InteracaoComentarioDTO;
import br.com.redesocial.model.*;
import br.com.redesocial.repository.*;
import br.com.redesocial.service.InteracaoComentarioService;
import br.com.redesocial.service.PerfilService;
import org.springframework.stereotype.Service;
import br.com.redesocial.repository.PublicacaoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InteracaoComentarioService {

    private final InteracaoComentarioRepository interacaoComentarioRepository;
    private final PerfilService perfilService;
    private final PerfilRepository perfilRepository;
    private final PublicacaoRepository publicacaoRepository;

    public InteracaoComentarioService(InteracaoComentarioRepository interacaoComentarioRepository, PerfilService perfilService, PerfilRepository perfilRepository, PublicacaoRepository publicacaoRepository) {
        this.interacaoComentarioRepository = interacaoComentarioRepository;
        this.perfilService = perfilService;
        this.perfilRepository = perfilRepository;
        this.publicacaoRepository = publicacaoRepository;
    }

    public InteracaoComentario criarInteracaoComentario(InteracaoComentarioDTO interacaoComentarioDTO){
        Perfil perfil = perfilRepository.findById(interacaoComentarioDTO.getUsuarioId())
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado"));

        Publicacao publicacao = publicacaoRepository.findById(interacaoComentarioDTO.getPublicacaoId())
                .orElseThrow(()-> new RuntimeException("Publicação não encontrada"));

        InteracaoComentario comentario = new InteracaoComentario();
        comentario.setPerfil(perfil);
        comentario.setPublicacao(publicacao);
        comentario.setDataHora(LocalDateTime.now());
        comentario.setTexto(interacaoComentarioDTO.getConteudo());

        return interacaoComentarioRepository.save(comentario);
    }

    public List<InteracaoComentario> listarInteracocomentario(){
        return interacaoComentarioRepository.findAll();
    }

    public void deletarInteracocomentario(Long id){
        interacaoComentarioRepository.deleteById(id);
    }
}
