package br.com.redesocial.repository;

import br.com.redesocial.model.Participa;
import br.com.redesocial.model.ParticipaId;
import br.com.redesocial.model.Conversa;
import br.com.redesocial.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ParticipaRepository extends JpaRepository<Participa, ParticipaId> {
    public List<Participa> findByPerfil(Perfil perfil);

    public List<Participa> findByConversa(Conversa conversa);

    boolean existsByPerfilAndConversa(Perfil perfil, Conversa conversa);

    void deleteByConversa(Conversa conversa);

    void deleteByPerfilAndConversa(Perfil perfil, Conversa conversa);
}
