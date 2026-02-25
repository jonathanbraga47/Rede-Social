package br.com.redesocial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publicacao")
public class Publicacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacao")
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Interacao> interacoes;

    @Column(name = "data_hora",nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "legenda",nullable = false)
    private String legenda;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ArquivoMidia> arquivos = new ArrayList<>();



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @JsonIgnore
    public Perfil getPerfil() {
        return perfil;
    }
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    public String getLegenda() {
        return legenda;
    }
    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }
    public List<ArquivoMidia> getArquivos() {return arquivos;}
    public void setArquivos(List<ArquivoMidia> arquivos) {this.arquivos = arquivos;}
    public List<Interacao> getInteracoes() {return interacoes;}
    public void setInteracoes(List<Interacao> interacoes) {this.interacoes = interacoes;}
}
