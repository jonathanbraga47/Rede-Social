package br.com.redesocial.service;

import br.com.redesocial.dto.SegueDTO;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.model.Segue;
import br.com.redesocial.model.SegueId;
import br.com.redesocial.repository.PerfilRepository;
import br.com.redesocial.repository.SegueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SegueService {
    @Autowired
    private SegueRepository segueRepository;

    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private ConversaService conversaService;

    @Transactional
    public SegueDTO seguirPerfil(Long idPerfilSeguidor, Long idPerfilSeguido){
        //validação para não seguir a si mesmo
        if(idPerfilSeguidor.equals(idPerfilSeguido)){
            throw new RuntimeException("Não é possível seguir a sí próprio");
        }

        //bucsa perfil
        Perfil seguidor = perfilRepository.findById(idPerfilSeguidor)
                .orElseThrow(()-> new RuntimeException("Perfil seguidor não encontrado"));

        Perfil seguido = perfilRepository.findById(idPerfilSeguido)
                .orElseThrow(()-> new RuntimeException("Perfil a ser seguido não encontrado"));

        //verifica se já segue
        if(segueRepository.existsBySeguidorAndSeguido(seguidor, seguido)){
            throw new RuntimeException("Perfil já seguido");
        }

        //cria relacionamento
        Segue segue = new Segue(seguidor, seguido);
        segueRepository.save(segue);

        conversaService.criarConversa(idPerfilSeguidor, idPerfilSeguido);

        return convertToDTO(segue);
    }

    @Transactional
    public void deixarDeSeguir(Long idPerfilSeguidor, Long idPerfilSeguido){
        Perfil seguidor = perfilRepository.findById(idPerfilSeguidor)
                .orElseThrow(()-> new RuntimeException("Perfil seguidor não encontrado"));

        Perfil seguido = perfilRepository.findById(idPerfilSeguido)
                .orElseThrow(()-> new RuntimeException(("Perfil seguido não encontrado")));

        //verifica se existe
        if(!segueRepository.existsBySeguidorAndSeguido(seguidor, seguido)){
            throw new RuntimeException("Perfil não seguido");
        }

        SegueId id = new SegueId(idPerfilSeguidor, idPerfilSeguido);
        segueRepository.deleteById(id);
    }

    public List<SegueDTO> listarSeguidores(Long perfilId){
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado"));

        return segueRepository.findBySeguido((perfil))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SegueDTO> listarSeguindo(Long perfilId){
        Perfil perfil = perfilRepository.findById((perfilId))
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado"));

        return segueRepository.findBySeguidor(perfil)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    public boolean verificarSeSeguePerfil(Long idPerfilSeguidor, Long idPerfilSeguido){
        Perfil seguidor = perfilRepository.findById(idPerfilSeguidor)
                .orElseThrow(()-> new RuntimeException("Perfil seguidor não encontrado"));

        Perfil seguido = perfilRepository.findById(idPerfilSeguido)
                .orElseThrow(()-> new RuntimeException("Perfil seguido não encontrado"));

        return segueRepository.existsBySeguidorAndSeguido(seguidor, seguido);
    }

    public Long contarSeguidores(Long perfilId){
        if(!perfilRepository.existsById(perfilId)){
            throw new RuntimeException("Perfil não encontrado");
        }
        return segueRepository.countSeguidoresByPerfilId(perfilId);
    }

    public Long contarSeguindo(Long perfilId){
        if(!perfilRepository.existsById(perfilId)){
            throw new RuntimeException("Perfil não encontrado");
        }
        return segueRepository.countSeguindoByPerfilId(perfilId);
    }

    private SegueDTO convertToDTO(Segue segue) {
        SegueDTO dto = new SegueDTO();
        dto.setIdPerfilSeguidor(segue.getSeguidor().getId());
        dto.setIdPerfilSeguido(segue.getSeguido().getId());
        dto.setSeguidorNome(segue.getSeguidor().getNome());
        dto.setSeguidorEmail(segue.getSeguidor().getEmail());
        dto.setSeguidoNome(segue.getSeguido().getNome());
        dto.setSeguidoEmail(segue.getSeguido().getEmail());
        return dto;
    }
}
