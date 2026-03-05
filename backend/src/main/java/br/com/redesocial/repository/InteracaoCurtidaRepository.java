package br.com.redesocial.repository;

import br.com.redesocial.model.InteracaoCurtida;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InteracaoCurtidaRepository extends JpaRepository<InteracaoCurtida, Long> {
    List<InteracaoCurtida> findByPerfilId(Long idPerfil);
}
