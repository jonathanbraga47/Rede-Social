package br.com.redesocial.service;

import br.com.redesocial.dto.PerfilDTO;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.repository.PerfilRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import br.com.redesocial.repository.ParticipaRepository;
import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ParticipaRepository participaRepository;

    public Perfil createPerfil(PerfilDTO perfilDTO){
        if(perfilRepository.existsByEmail(perfilDTO.getEmail())){
            throw new RuntimeException("Email já cadastrado");
        }

        Perfil perfil = new Perfil();
        perfil.setEmail(perfilDTO.getEmail());
        perfil.setNome(perfilDTO.getNome());
        perfil.setSenha(perfilDTO.getSenha());

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

        return perfilRepository.save(perfil);
    }

    @Transactional
    public void deletePerfil(Long Id){
        Perfil perfil = perfilRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        perfilRepository.delete(perfil);
    }

    public List<Publicacao> listarPublicacoesDoPerfil(Long id) {
        // Busca o perfil ou lança exceção se não encontrar
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        // Retorna a lista de publicações associada ao perfil
        return perfil.getPublicacoes();
    }

}
