package br.com.redesocial.repository;

import br.com.redesocial.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "DELETE FROM perfil WHERE id_perfil = :id", nativeQuery = true)
    void deleteByIdNative(@Param("id") Long id);
}
