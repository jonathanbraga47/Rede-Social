package br.com.redesocial.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "view_perfis_inatividade")
public class ViewPerfisInativos {

    @Id
    @Column(name = "id_perfil")
    private Long idPerfil;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "total_publicacoes")
    private int totalPublicacoes;

    @Column(name = "total_interacoes")
    private int totalInteracoes;

    @Column(name = "total_seguindo")
    private int totalSeguindo;

    @Column(name = "total_seguidores")
    private int totalSeguidores;

    @Column(name = "status_atividade")
    private String statusAtividade;

    public Long getIdPerfil() { return idPerfil; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public int getTotalPublicacoes() { return totalPublicacoes; }
    public int getTotalInteracoes() { return totalInteracoes; }
    public int getTotalSeguindo() { return totalSeguindo; }
    public int getTotalSeguidores() { return totalSeguidores; }
    public String getStatusAtividade() { return statusAtividade; }
}