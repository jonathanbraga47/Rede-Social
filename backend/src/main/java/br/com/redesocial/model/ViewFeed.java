package br.com.redesocial.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "view_feed")
public class ViewFeed {
    @Id
    @Column(name = "nome")
    private String nome;

    @Column(name = "legenda")
    private String legenda;

    @Column(name = "url_midia")
    private String urlMidia;

    @Column(name = "total_curtidas")
    private Integer totalCurtidas;

    @Column(name = "total_comentarios")
    private Integer totalComentarios;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    public String getNome() {return nome;}
    public String getLegenda() {return legenda;}
    public String getUrlMidia() {return urlMidia;}
    public Integer getTotalCurtidas() {return totalCurtidas;}
    public Integer getTotalComentarios() {return totalComentarios;}
    public LocalDateTime getDataHora() {return dataHora;}

}
