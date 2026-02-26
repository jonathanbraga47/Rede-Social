package br.com.redesocial.repository;

import br.com.redesocial.model.Segue;
import br.com.redesocial.model.SegueId;
import br.com.redesocial.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegueRepository extends JpaRepository<Segue, SegueId> {
    List<Segue> findBySeguido(Perfil seguido);

    List<Segue> findBySeguidor(Perfil seguidor);

    //se um perfil segue o outro
    boolean existsBySeguidorAndSeguido(Perfil seguidor, Perfil seguido);

    //conta quantos seguidores um perfil tem
    @Query("SELECT COUNT(s) FROM Segue s WHERE s.seguido.id = :perfilId")
    Long countSeguidoresByPerfilId(@Param("perfilId") Long perfilId);

    //conta quantos perfis um usuário segue
    @Query("SELECT COUNT(s) FROM Segue s WHERE s.seguidor.id = :perfilId")
    Long countSeguindoByPerfilId(@Param("perfilId") Long perfilId);

    //deleta o relacionamento
    void deleteBySeguidorAndSeguido(Perfil seguidor, Perfil seguido);
}
