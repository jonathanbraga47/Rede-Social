package br.com.redesocial.repository;

import br.com.redesocial.model.Conversa;
import br.com.redesocial.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    List<Conversa> findByPerfil(Perfil perfil);

    //Conversa onde o perfil participa
    @Query("SELECT DISTINCT c FROM Conversa c LEFT JOIN c.participantes p " +
            "WHERE c.perfil.id = :perfilId OR p.perfil.id = :perfilId")
        List<Conversa> findByPerfilId(@Param("perfilId") Long perfilId);

    //Busca conversa entre dois perfis
    @Query("SELECT c FROM Conversa c JOIN c.participantes p1 JOIN c.participantes p2 " +
            "WHERE (c.perfil.id = :perfil1Id AND p1.perfil.id = :perfil2Id) " +
            "OR (c.perfil.id = :perfil2Id AND p2.perfil.id = :perfil1Id) " +
            "AND c.tipoConversa = 'privada'")
    Optional<Conversa> findConversaEntrePerfis(@Param("perfil1id") Long perfil1Id, @Param("perfil2id") Long perfil2Id);
}


