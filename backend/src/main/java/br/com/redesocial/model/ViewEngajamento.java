package br.com.redesocial.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "view_engajamento")
public class ViewEngajamento {
    @Id
    @Column(name = "id_publicacao")
    private Long idPublicacao;

    @Column(name = "autor")
    private String autor;

    @Column(name = "url_midia")
    private String urlMidia;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "legenda")
    private String legenda;

    @Column(name = "total_interacoes")
    private int totalInteracoes;

    public Long getIdPublicacao() {return idPublicacao;}
    public String getAutor() {return autor;}
    public String getUrlMidia() {return urlMidia;}
    public LocalDateTime getDataHora() {return dataHora;}

    public int getTotalInteracoes() {return totalInteracoes;}
    public String getLegenda() {return legenda;}
}
