package br.com.redesocial.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "view_ranking_usuarios")
public class ViewRankingUsuarios {
    @Id
    @Column(name = "id_perfil")
    private Long idPerfil;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "total_publicacoes")
    private int totalPublicacoes;

    @Column(name = "total_interacoes_feitas")
    private int totalInteracoesFeitas;


    @Column(name = "curtidas_recebidas")
    private int curtidasRecebidas;

    @Column(name = "comentarios_recebidos")
    private int comentariosRecebidos;

    @Column(name = "total_seguidores")
    private int totalSeguidores;

    @Column(name = "total_seguindo")
    private int totalSeguindo;

    @Column(name = "score_engajamento")
    private int scoreEngajamento;

    public Long getIdPerfil() { return idPerfil; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public int getTotalPublicacoes() { return totalPublicacoes; }
    public int getTotalInteracoesFeiras() { return totalInteracoesFeitas; }
    public int getCurtidasRecebidas() { return curtidasRecebidas; }
    public int getComentariosRecebidos() { return comentariosRecebidos; }
    public int getTotalSeguidores() { return totalSeguidores; }
    public int getTotalSeguindo() { return totalSeguindo; }
    public int getScoreEngajamento() { return scoreEngajamento; }


}