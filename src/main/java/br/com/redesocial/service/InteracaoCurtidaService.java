package br.com.redesocial.service;

import br.com.redesocial.dto.InteracaoCurtidaDTO;
import br.com.redesocial.model.InteracaoComentario;
import br.com.redesocial.model.InteracaoCurtida;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.repository.InteracaoCurtidaRepository;
import br.com.redesocial.repository.PerfilRepository;
import br.com.redesocial.repository.PublicacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InteracaoCurtidaService {
    private final InteracaoCurtidaRepository interacaoCurtidaRepository;
    private final PerfilRepository perfilRepository;
    private final PublicacaoRepository publicacaoRepository;

    public InteracaoCurtidaService(InteracaoCurtidaRepository interacaoCurtidaRepository, PerfilRepository perfilRepository, PublicacaoRepository publicacaoRepository) {
        this.interacaoCurtidaRepository = interacaoCurtidaRepository;
        this.perfilRepository = perfilRepository;
        this.publicacaoRepository = publicacaoRepository;
    }

    public InteracaoCurtida criarInteracaoCurtida(InteracaoCurtidaDTO interacaoCurtidaDTO) {
        Perfil perfil = perfilRepository.findById(interacaoCurtidaDTO.getUsuarioId())
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado"));

        Publicacao publicacao = publicacaoRepository.findById(interacaoCurtidaDTO.getPublicacaoId())
                .orElseThrow(()-> new RuntimeException("Publicação não encontrada"));

        InteracaoCurtida curtida = new InteracaoCurtida();
        curtida.setPerfil(perfil);
        curtida.setPublicacao(publicacao);
        curtida.setDataHora(LocalDateTime.now());

        return interacaoCurtidaRepository.save(curtida);
    }

    public List<InteracaoCurtida> listarInteracaoCurtida() {
        return interacaoCurtidaRepository.findAll();
    }

    public void deletarInteracaoCurtida(Long id){
        interacaoCurtidaRepository.deleteById(id);
    }

}
