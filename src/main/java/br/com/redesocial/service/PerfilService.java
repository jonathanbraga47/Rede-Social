package br.com.redesocial.service;

import br.com.redesocial.dto.PerfilDTO;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public Perfil createPerfil(PerfilDTO perfilDTO){
        //return perfilRepository.save(perfilDTO);
        return new Perfil(); //temporario
    }
}
