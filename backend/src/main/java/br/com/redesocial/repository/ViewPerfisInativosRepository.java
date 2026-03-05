package br.com.redesocial.repository;

import br.com.redesocial.model.ViewPerfisInativos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewPerfisInativosRepository extends JpaRepository<ViewPerfisInativos, Long> {
}