package br.com.redesocial.repository;

import br.com.redesocial.model.ViewEngajamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewEngajamentoRepository extends JpaRepository<ViewEngajamento, Long> {
}
