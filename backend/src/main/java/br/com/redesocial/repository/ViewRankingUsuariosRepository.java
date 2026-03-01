package br.com.redesocial.repository;

import br.com.redesocial.model.ViewRankingUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRankingUsuariosRepository extends JpaRepository<ViewRankingUsuarios, Long> {
}