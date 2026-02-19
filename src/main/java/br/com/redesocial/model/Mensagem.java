package br.com.redesocial.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensagem")
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensagem")
    private Long idMensagem;

    @ManyToOne
    @JoinColumn(name = "Id_conversa", nullable = false)
    private Conversa conversa;

    @ManyToOne
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();

    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    public Mensagem() {}

    public Mensagem(Conversa conversa, Perfil perfil, String conteudo) {
        this.conversa = conversa;
        this.perfil = perfil;
        this.conteudo = conteudo;
        this.dataHora = LocalDateTime.now();
    }

    public Long getIdMensagem() { return idMensagem; }
    public void setIdMensagem(Long idMensagem) { this.idMensagem = idMensagem; }
    public Conversa getConversa() { return conversa; }
    public void setConversa(Conversa conversa) { this.conversa = conversa; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
}

