package br.com.redesocial.repository;

import br.com.redesocial.model.Conversa;
import br.com.redesocial.model.Perfil;
import br.com.redesocial.model.Mensagem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {

    //Conversa onde o perfil participa
    @Query("""
        SELECT DISTINCT c
        FROM Conversa c
        JOIN c.participantes p
        WHERE p.perfil.id = :perfilId
    """)
    List<Conversa> findConversasDoPerfil(@Param("perfilId") Long perfilId);

    //Busca conversa entre dois perfis
    @Query("""
    SELECT c
    FROM Conversa c
    JOIN c.participantes p
    GROUP BY c
    HAVING COUNT(DISTINCT p.perfil.id) = 2
       AND SUM(CASE WHEN p.perfil.id IN (:perfil1Id, :perfil2Id) THEN 1 ELSE 0 END) = 2
""")
    Optional<Conversa> findConversaEntrePerfis(
            @Param("perfil1Id") Long perfil1Id,
            @Param("perfil2Id") Long perfil2Id
    );
}


