package br.com.redesocial.service;

import br.com.redesocial.dto.ArquivoMidiaDTO;
import br.com.redesocial.model.ArquivoMidia;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.repository.ArquivoMidiaRepository;
import br.com.redesocial.repository.PublicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArquivoMidiaService {
    @Autowired
    private ArquivoMidiaRepository arquivoMidiaRepository;

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Transactional
    public ArquivoMidia adicionarArquivo(ArquivoMidiaDTO dto) {
        Publicacao publicacao = publicacaoRepository.findById(dto.getIdPublicacao())
                .orElseThrow(()-> new RuntimeException("Publicação não encontrada"));

        ArquivoMidia arquivoMidia = new ArquivoMidia();
        arquivoMidia.setPublicacao(publicacao);
        arquivoMidia.setUrlMidia(dto.getTipoMidia());

        return arquivoMidiaRepository.save(arquivoMidia);
    }

    public List<ArquivoMidiaDTO> listarArquivosPorPublicacao(Long idPublicacao) {
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao)
                .orElseThrow(()-> new RuntimeException("Publicação não encontrada"));

        return arquivoMidiaRepository.findByPublicacao(publicacao)
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    public ArquivoMidiaDTO buscarArquivo(Long idArquivo) {
        ArquivoMidia arquivo = arquivoMidiaRepository.findById(idArquivo)
                .orElseThrow(()-> new RuntimeException("Arquivo não encontrado"));

        return convertDTO(arquivo);
    }

    @Transactional
    public void deletarArquivo(Long idArquivo) {
        if(!arquivoMidiaRepository.existsById(idArquivo)){
            throw new RuntimeException("Arquivo não encontrado");
        }
        arquivoMidiaRepository.deleteById(idArquivo);
    }

    private ArquivoMidiaDTO convertDTO(ArquivoMidia arquivoMidia) {
        ArquivoMidiaDTO dto = new ArquivoMidiaDTO();
        dto.setIdArquivo(arquivoMidia.getIdArquivo());
        dto.setTipoMidia(arquivoMidia.getUrlMidia());
        dto.setIdPublicacao(arquivoMidia.getPublicacao().getId());
        return dto;
    }


}
