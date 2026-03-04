package br.com.redesocial.repository;

import br.com.redesocial.model.InteracaoComentario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InteracaoComentarioRepository extends JpaRepository<InteracaoComentario, Long> {
    List<InteracaoComentario> findByPerfilId(Long idPerfil);
}


