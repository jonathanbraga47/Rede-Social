package br.com.redesocial.repository;


import br.com.redesocial.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {
    // Não precisa de @Query complexa aqui. O @BatchSize resolve o carregamento.
    List<Publicacao> findByPerfilId(Long perfilId);
}
