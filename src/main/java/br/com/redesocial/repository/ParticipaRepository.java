package br.com.redesocial.repository;

import br.com.redesocial.model.Participa;
import br.com.redesocial.model.ParticipaId;
import br.com.redesocial.model.Conversa;
import br.com.redesocial.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ParticipaRepository extends JpaRepository<Participa, ParticipaId> {
    public List<Participa> findByPerfil(Perfil perfil);

    public List<Participa> findByConversa(Conversa conversa);

    boolean existsByPerfilAndConversa(Perfil perfil, Conversa conversa);

    void deleteByConversa(Conversa conversa);

    void deleteByPerfilAndConversa(Perfil perfil, Conversa conversa);

    @Query("SELECT COUNT(p1) > 0 FROM Participa p1 " +
            "JOIN Participa p2 ON p1.conversa = p2.conversa " +
            "WHERE p1.perfil.id = :id1 AND p2.perfil.id = :id2")
    boolean existsConversaEntreUsuarios(@Param("id1") Long id1, @Param("id2") Long id2);
}