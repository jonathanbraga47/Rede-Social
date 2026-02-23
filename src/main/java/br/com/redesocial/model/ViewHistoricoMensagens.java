package br.com.redesocial.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "view_historico_mensagens")
public class ViewHistoricoMensagens {
    @Id
    @Column(name = "id_conversa")
    private Long idConversa;

    @Column(name = "nome")
    private String nome;

    @Column(name = "mensagem")
    private String mensagem;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    public Long getIdConversa() { return idConversa; }
    public String getNome() { return nome; }
    public String getMensagem() { return mensagem; }
    public LocalDateTime getDataHora() { return dataHora; }
}
