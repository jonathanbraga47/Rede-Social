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

    public Publicacao createPublicacao(PublicacaoDTO publicacaoDTO) {
        Perfil perfil = perfilRepository.findById(publicacaoDTO.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Publicacao publicacao = new Publicacao();
        publicacao.setPerfil(perfil);
        publicacao.setLegenda(publicacaoDTO.getLegenda());
        publicacao.setDataHora(LocalDateTime.now()); // Data gerada automaticamente

        return publicacaoRepository.save(publicacao);
    }

    public List<Publicacao> getAllPublicacao(){
        return publicacaoRepository.findAll();
    }

    public Publicacao getPublicacao(Long id) {
        return publicacaoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Publicação não encontrada"));
    }

    public Publicacao updatePublicacao(Long id, PublicacaoDTO publicacaoDTO) {
        Publicacao publicacao = getPublicacao(id);

        if(publicacaoDTO.getLegenda() != null && !publicacaoDTO.getLegenda().isBlank()) {
            publicacao.setLegenda(publicacaoDTO.getLegenda());
        }

        return publicacaoRepository.save(publicacao);
    }

    public void deletePublicacao(Long id) {
        if (!publicacaoRepository.existsById(id)) {
            throw new RuntimeException("Publicação não encontrada para exclusão");
        }
        publicacaoRepository.deleteById(id);
    }
}
