package br.com.redesocial.service;

import br.com.redesocial.dto.ConversaDTO;
import br.com.redesocial.dto.ParticipaDTO;
import br.com.redesocial.model.Conversa;
import br.com.redesocial.model.Participa;
import br.com.redesocial.model.ParticipaId;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.repository.ConversaRepository;
import br.com.redesocial.repository.ParticipaRepository;
import br.com.redesocial.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipaService {
    @Autowired
    private ParticipaRepository participaRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ConversaRepository conversaRepository;

    @Transactional
    public ConversaDTO criarConversa(Long idPerfilSeguidor, Long idPerfilSeguido){
        Perfil seguidor = perfilRepository.findById(idPerfilSeguidor)
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado"));

        Perfil seguido = perfilRepository.findById(idPerfilSeguido)
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado"));

        Optional<Conversa> conversaExistente = buscarConversaEntrePerfis(idPerfilSeguidor, idPerfilSeguido);

        if(conversaExistente.isPresent()){
            return convertConversaToDTO(conversaExistente.get());
        }

        Conversa conversa = new Conversa();
        Conversa conversaSalva = conversaRepository.save(conversa);

        participaRepository.save(new Participa(seguidor, conversaSalva));
        participaRepository.save(new Participa(seguido, conversaSalva));

        return convertConversaToDTO(conversaSalva);
    }

    public Optional<Conversa> buscarConversaEntrePerfis(Long idPerfil1, Long idPerfil2){
        return conversaRepository.findConversaEntrePerfis(idPerfil1, idPerfil2);
    }

    public List<ConversaDTO> listarConversasDePerfil(Long idPerfil){
        if(!perfilRepository.existsById(idPerfil)){
            throw new RuntimeException("Perfil não encontrado");
        }

        return conversaRepository.findConversasDoPerfil(idPerfil)
                .stream()
                .map(this::convertConversaToDTO)
                .collect(Collectors.toList());
    }

    public ConversaDTO buscarConversa(Long idConversa){
        Conversa conversa = conversaRepository.findById(idConversa)
                .orElseThrow(()-> new RuntimeException("Conversa não encontrada"));

        return convertConversaToDTO(conversa);
    }

    @Transactional
    public void removerParticipante(Long idPerfil, Long idConversa){
        Perfil perfil = perfilRepository.findById(idPerfil)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Conversa conversa = conversaRepository.findById(idConversa)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));

        if(!participaRepository.existsByPerfilAndConversa(perfil, conversa)){
            throw new RuntimeException("Perfil não participa desta conversa");
        }

        ParticipaId id = new ParticipaId(idPerfil, idConversa);
        participaRepository.deleteById(id);
    }

    @Transactional
    public void deletarCOnversa(Long idConversa){
        if (!conversaRepository.existsById(idConversa)) {
            throw new RuntimeException("Conversa não encontrada");
        }
        conversaRepository.deleteById(idConversa);
    }

    public boolean verificaParticipacao(Long idPerfil, Long idConversa){
        Perfil perfil = perfilRepository.findById(idPerfil)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Conversa conversa = conversaRepository.findById(idConversa)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));

        return participaRepository.existsByPerfilAndConversa(perfil, conversa);
    }

    private ConversaDTO convertConversaToDTO(Conversa conversa){
        ConversaDTO dto = new ConversaDTO();
        dto.setIdConversa(conversa.getIdConversa());

        List<ParticipaDTO> participantes = participaRepository.findByConversa(conversa)
                .stream()
                .map(this::convertParticipaToDTO)
                .collect(Collectors.toList());

        dto.setParticipantes(participantes);
        return dto;
    }

    private ParticipaDTO convertParticipaToDTO(Participa participa){
        ParticipaDTO dto = new ParticipaDTO();
        dto.setIdPerfil(participa.getPerfil().getId());
        dto.setPerfilNome(participa.getPerfil().getNome());
        dto.setPerfilEmail(participa.getPerfil().getEmail());
        return dto;
    }
}
