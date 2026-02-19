package br.com.redesocial.service;

import br.com.redesocial.dto.MensagemDTO;
import br.com.redesocial.dto.MensagemRequestDTO;
import br.com.redesocial.model.Conversa;
import br.com.redesocial.model.Mensagem;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.repository.ConversaRepository;
import br.com.redesocial.repository.MensagemRepository;
import br.com.redesocial.repository.ParticipaRepository;
import br.com.redesocial.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private ConversaRepository conversaRepository;

    @Autowired
    private ParticipaRepository participaRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Transactional
    public MensagemDTO enviarMensagem(MensagemRequestDTO mensagemRequestDTO) {
        Conversa conversa = conversaRepository.findById(mensagemRequestDTO.getIdConversa())
                .orElseThrow(()-> new RuntimeException("Conversa não encontrada"));

        Perfil perfil = perfilRepository.findById(mensagemRequestDTO.getIdPerfil())
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado"));

        boolean participa = participaRepository.existsByPerfilAndConversa(perfil, conversa);
        boolean criador = conversa.getPerfil().getId().equals(perfil.getId());

        if(!participa && !criador) {
            throw new RuntimeException("Perfil não participa desta conversa");
        }

        Mensagem mensagem = new Mensagem(conversa, perfil, mensagemRequestDTO.getConteudo());
        return convertToDTO(mensagemRepository.save(mensagem));
    }

    public List<MensagemDTO> listarMensagensDaConversa(Long idConversa){
        Conversa conversa = conversaRepository.findById(idConversa)
                .orElseThrow(()-> new RuntimeException("Conversa não encontrada"));

        return mensagemRepository.findByConversaOrderByDataHoraAsc(conversa)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MensagemDTO buscarMensagem(Long idMensagem){
        return convertToDTO(mensagemRepository.findById(idMensagem)
                .orElseThrow(()-> new RuntimeException()));
    }

    @Transactional
    public void deletarMensagem(Long idMensagem){
        if(!mensagemRepository.existsById(idMensagem)) {
            throw new RuntimeException("Mensagem não encontrada");
        }
        mensagemRepository.deleteById(idMensagem);
    }

    private MensagemDTO convertToDTO(Mensagem mensagem) {
        MensagemDTO dto = new MensagemDTO();
        dto.setIdMensagem(mensagem.getIdMensagem());
        dto.setIdConversa(mensagem.getConversa().getIdConversa());
        dto.setIdPerfil(mensagem.getPerfil().getId());
        dto.setPerfilNome(mensagem.getPerfil().getNome());
        dto.setDataHora(mensagem.getDataHora());
        dto.setConteudo(mensagem.getConteudo());
        return dto;
    }
}
