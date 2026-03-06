package br.com.redesocial.service;

import br.com.redesocial.dto.InteracaoDTO;
import br.com.redesocial.dto.PerfilDTO;
import br.com.redesocial.dto.PublicacaoDTO;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.model.Publicacao;
import br.com.redesocial.repository.PerfilRepository;
import br.com.redesocial.repository.PublicacaoRepository;
import org.springframework.transaction.annotation.Transactional; // ✅ Adicione esta
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

    @Autowired
    private PublicacaoRepository publicacaoRepository;

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
    public void deletePerfil(Long id) {
        if (!perfilRepository.existsById(id)) {
            throw new RuntimeException("Perfil não encontrado");
        }
        // Deleta diretamente via SQL nativo
        perfilRepository.deleteByIdNative(id);
    }

    @Transactional(readOnly = true)
    public List<Publicacao> listarPublicacoesDoPerfil(Long id) {
        // Busca as publicações (os filhos 'arquivos' e 'interacoes' virão via BatchSize)
        List<Publicacao> publicacoes = publicacaoRepository.findByPerfilId(id);

        if (publicacoes.isEmpty() && !perfilRepository.existsById(id)) {
            throw new RuntimeException("Perfil não encontrado");
        }

        // Opcional: Forçar inicialização para garantir que o erro não ocorra no Jackson
        publicacoes.forEach(p -> {
            p.getInteracoes().size();
            p.getArquivos().size();
        });

        return publicacoes;
    }

}
