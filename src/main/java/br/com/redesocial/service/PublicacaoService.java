package br.com.redesocial.service;

import br.com.redesocial.dto.PublicacaoDTO;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.repository.PerfilRepository;
import br.com.redesocial.repository.PublicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicacaoService {
    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public Publicacao criarPublicacao(PublicacaoDTO publicacaoDTO) {
        Perfil perfil = perfilRepository.findById(publicacaoDTO.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Publicacao publicacao = new Publicacao();
        publicacao.setPerfil(perfil);
        publicacao.setLegenda(publicacaoDTO.getLegenda());
        publicacao.setDataHora(LocalDateTime.now());

        return publicacaoRepository.save(publicacao);
    }

    public List<Publicacao> listarPublicacao(){
        return publicacaoRepository.findAll();
    }

    public Publicacao buscarPublicacao(Long id) {
        return publicacaoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Publicação não encontrada"));
    }

    public Publicacao atualizarPublicacao(Long id, PublicacaoDTO publicacaoDTO) {
        Publicacao publicacao = buscarPublicacao(id);

        if(publicacaoDTO.getLegenda() != null) {
            publicacao.setLegenda(publicacaoDTO.getLegenda());
        }

        return publicacaoRepository.save(publicacao);
    }

    public void deletarPublicacao(Long id) {
        Publicacao publicacao = buscarPublicacao(id);
        publicacaoRepository.delete(publicacao);
    }
}
