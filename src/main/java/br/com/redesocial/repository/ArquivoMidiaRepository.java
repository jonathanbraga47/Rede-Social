package br.com.redesocial.repository;

import br.com.redesocial.model.ArquivoMidia;
import br.com.redesocial.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArquivoMidiaRepository extends JpaRepository<ArquivoMidia, Long> {
    List<ArquivoMidia> findByPublicacao(Publicacao publicacao);

    void deleteByPublicacao(Publicacao publicacao);

}
