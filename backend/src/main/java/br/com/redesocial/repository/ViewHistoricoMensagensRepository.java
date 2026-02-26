package br.com.redesocial.repository;

import br.com.redesocial.model.ViewHistoricoMensagens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ViewHistoricoMensagensRepository extends JpaRepository<ViewHistoricoMensagens,Long> {
    List<ViewHistoricoMensagens> findByIdConversa(Long idConversa);
}
