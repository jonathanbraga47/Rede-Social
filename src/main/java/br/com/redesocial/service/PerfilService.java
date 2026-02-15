package br.com.redesocial.service;

import br.com.redesocial.dto.PerfilDTO;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public Perfil createPerfil(PerfilDTO perfilDTO){
        if(perfilRepository.existsByEmail(perfilDTO.getEmail())){
            throw new RuntimeException("Email já cadastrado");
        }

        Perfil perfil = new Perfil();
        perfil.setEmail(perfilDTO.getEmail());
        perfil.setNome(perfilDTO.getNome());
        perfil.setSenha(perfilDTO.getSenha());
        perfil.setTipoPrivacidade(perfilDTO.getTipoPrivacidade());

        return perfilRepository.save(perfil);
    }

    public List<Perfil> getAllPerfil(){
        return perfilRepository.findAll();
    }

    public Perfil getPerfil(Long id){
        return perfilRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Perfil não encontrado"));
    }

    public Perfil updatePerfil(Long id, PerfilDTO perfilDTO) {
        Perfil perfil = getPerfil(id);

        if (perfilDTO.getNome() != null) {
            perfil.setNome(perfilDTO.getNome());
        }

        if (perfilDTO.getEmail() != null) {
            if (!perfil.getEmail().equals(perfilDTO.getEmail()) && perfilRepository.existsByEmail(perfilDTO.getEmail())) {
                throw new RuntimeException(("Email já cadastrado"));
            }
            perfil.setEmail(perfilDTO.getEmail());
        }

        if (perfilDTO.getSenha() != null) {
            perfil.setSenha(perfilDTO.getSenha());
        }

        if(perfilDTO.getTipoPrivacidade() != null){
            perfil.setTipoPrivacidade(perfilDTO.getTipoPrivacidade());
        }

        return perfilRepository.save(perfil);
    }

    public void deletePerfil(Long Id){
        perfilRepository.deleteById(Id);
    }
}
