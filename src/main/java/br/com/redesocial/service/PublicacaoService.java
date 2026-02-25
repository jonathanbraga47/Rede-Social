package br.com.redesocial.service;
import br.com.redesocial.dto.PublicacaoDTO;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.repository.PerfilRepository;
import br.com.redesocial.repository.PublicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<PublicacaoDTO> getAllPublicacao(){
        List<PublicacaoDTO> publicacoes = new ArrayList<>();
        for(Publicacao p: publicacaoRepository.findAll()){
            publicacoes.add(convertToDTO(p));
        }
        return publicacoes;
    }

    public PublicacaoDTO getPublicacao(Long id) {
        Publicacao publicacao = publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        return convertToDTO(publicacao);
    }

    // Crie este método auxiliar para mapear os dados
    private PublicacaoDTO convertToDTO(Publicacao publicacao) {
        PublicacaoDTO dto = new PublicacaoDTO();
        dto.setId(publicacao.getId());
        dto.setLegenda(publicacao.getLegenda());
        dto.setDataHora(publicacao.getDataHora());
        dto.setInteracoes(publicacao.getInteracoes());

        // Aqui pegamos os dados do objeto Perfil que está dentro da Publicação
        if (publicacao.getPerfil() != null) {
            dto.setPerfilId(publicacao.getPerfil().getId());
        }

        return dto;
    }

    public PublicacaoDTO updatePublicacao(Long id, PublicacaoDTO publicacaoDTO) {
        // 1. Busca a ENTIDADE diretamente do repository
        Publicacao publicacao = publicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        // 2. Atualiza os campos necessários
        if(publicacaoDTO.getLegenda() != null && !publicacaoDTO.getLegenda().isBlank()) {
            publicacao.setLegenda(publicacaoDTO.getLegenda());
        }

        // 3. Salva a entidade
        Publicacao publicacaoAtualizada = publicacaoRepository.save(publicacao);

        // 4. Retorna o DTO (usando aquele método de conversão que criamos)
        return convertToDTO(publicacaoAtualizada);
    }

    public void deletePublicacao(Long id) {
        if (!publicacaoRepository.existsById(id)) {
            throw new RuntimeException("Publicação não encontrada para exclusão");
        }
        publicacaoRepository.deleteById(id);
    }
}
