package br.com.redesocial.service;

import br.com.redesocial.dto.InteracaoCurtidaDTO;
import br.com.redesocial.model.InteracaoComentario;
import br.com.redesocial.model.InteracaoCurtida;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.repository.InteracaoCurtidaRepository;
import br.com.redesocial.repository.PerfilRepository;
import br.com.redesocial.repository.PublicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InteracaoCurtidaService {

    @Autowired
    private InteracaoCurtidaRepository interacaoCurtidaRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private PublicacaoRepository publicacaoRepository;

    public InteracaoCurtida createInteracaoCurtida(InteracaoCurtidaDTO dto) {
        Perfil perfil = perfilRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Publicacao publicacao = publicacaoRepository.findById(dto.getPublicacaoId())
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        InteracaoCurtida curtida = new InteracaoCurtida();
        curtida.setPerfil(perfil);
        curtida.setPublicacao(publicacao);
        curtida.setDataHora(LocalDateTime.now());

        return interacaoCurtidaRepository.save(curtida);
    }

    public List<InteracaoCurtida> getAllInteracaoCurtida() {
        return interacaoCurtidaRepository.findAll();
    }

    public InteracaoCurtida getInteracaoCurtida(Long id){
        return interacaoCurtidaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Curtida não encontrado"));
    }

    public void deleteInteracaoCurtida(Long id){
        interacaoCurtidaRepository.deleteById(id);
    }

}
