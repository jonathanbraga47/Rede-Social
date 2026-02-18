package br.com.redesocial.service;

import br.com.redesocial.dto.ConversaDTO;
import br.com.redesocial.dto.ConversaRequestDTO;
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
public class ConversaService {
    @Autowired
    private ConversaRepository conversaRepository;

    @Autowired
    private ParticipaRepository participaRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Transactional
    public ConversaDTO criarConversa(Long idPerfilSeguidor, Long idPerfilSeguido){
        Perfil seguidor = perfilRepository.findById(idPerfilSeguidor)
                .orElseThrow(() -> new RuntimeException("Perfil seguidor não encontrado"));

        Perfil seguido = perfilRepository.findById(idPerfilSeguido)
                .orElseThrow(() -> new RuntimeException("Perfil seguido não encontrado"));

        Optional<Conversa> conversaExiste = buscarConversaEntrePerfis(idPerfilSeguidor, idPerfilSeguido);

        if(conversaExiste.isPresent()){
            return convertToDTO(conversaExiste.get());
        }

        Conversa conversa = new Conversa(seguidor);
        Conversa conversaSalva = conversaRepository.save(conversa);

        Participa participa = new Participa(seguido, conversaSalva);
        participaRepository.save(participa);

        return convertToDTO(conversaSalva);
    }

    public Optional<Conversa> buscarConversaEntrePerfis(Long idPerfil1, Long IdPerfil2){
        return conversaRepository.findConversaEntrePerfis(idPerfil1, IdPerfil2);
    }

    public List<ConversaDTO> listarConversasDoPerfil(Long perfilId){
        if(!perfilRepository.existsById(perfilId)){
            throw new RuntimeException("Perfil não encontrado");
        }

        return conversaRepository.findByPerfilId(perfilId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ConversaDTO buscarConversa(Long idConversa){
        Conversa conversa = conversaRepository.findById(idConversa)
                .orElseThrow(()-> new RuntimeException("Conversa não encontrada"));
        return convertToDTO(conversa);
    }

    public List<ParticipaDTO> listarParticipasDoConversa(Long idConversa){
        Conversa conversa = conversaRepository.findById(idConversa)
                .orElseThrow(()-> new RuntimeException("Conversa não encontrada"));

        return participaRepository.findByConversa(conversa)
                .stream()
                .map(this::convertParticipaToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removerParticipante(Long idPerfil, Long idConversa){
        Perfil perfil = perfilRepository.findById(idPerfil)
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado"));
        Conversa conversa = conversaRepository.findById(idConversa)
                .orElseThrow(()-> new RuntimeException("Conversa não encontrada"));

        if(!participaRepository.existsByPerfilAndConversa(perfil, conversa)){
            throw new RuntimeException("Perfil não participa desta conversa");
        }

        ParticipaId id = new ParticipaId(idPerfil, idConversa);
        participaRepository.deleteById(id);
    }

    public boolean verificarParticipacao(Long idPerfil, Long idConversa){
        Perfil perfil = perfilRepository.findById(idPerfil)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Conversa conversa = conversaRepository.findById(idConversa)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));

        participaRepository.existsByPerfilAndConversa(perfil, conversa);
        return true;
    }

    @Transactional
    public void deletarConversa(Long idConversa){
        if (!conversaRepository.existsById(idConversa)) {
            throw new RuntimeException("Conversa não encontrada");
        }
        conversaRepository.deleteById(idConversa);
    }

    private ConversaDTO convertToDTO(Conversa conversa) {
        ConversaDTO dto = new ConversaDTO();
        dto.setIdConversa(conversa.getIdConversa());
        dto.setIdPerfil(conversa.getPerfil().getId());
        dto.setPerfilNome(conversa.getPerfil().getNome());
        dto.setPerfilEmail(conversa.getPerfil().getEmail());
        dto.setTipoConversa(conversa.getTipoConversa());

        // Lista de participantes (não inclui o criador)
        List<ParticipaDTO> participantes = participaRepository.findByConversa(conversa)
                .stream()
                .map(this::convertParticipaToDTO)
                .collect(Collectors.toList());

        dto.setParticipantes(participantes);
        return dto;
    }

    private ParticipaDTO convertParticipaToDTO(Participa participa) {
        ParticipaDTO dto = new ParticipaDTO();
        dto.setIdPerfil(participa.getPerfil().getId());
        dto.setIdConversa(participa.getConversa().getIdConversa());
        dto.setPerfilNome(participa.getPerfil().getNome());
        dto.setPerfilEmail(participa.getPerfil().getEmail());
        return dto;
    }

}
