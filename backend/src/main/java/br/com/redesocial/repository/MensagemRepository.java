package br.com.redesocial.repository;

import br.com.redesocial.model.Conversa;
import br.com.redesocial.model.Mensagem;
import br.com.redesocial.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByConversa(Conversa conversa);
    List<Mensagem> findByPerfil(Perfil perfil);
    List<Mensagem> findByConversaOrderByDataHoraAsc(Conversa conversa);
}
