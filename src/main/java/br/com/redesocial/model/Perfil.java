package br.com.redesocial.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import br.com.redesocial.enums.TipoPerfil;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column( name = "tipo_privacidade", nullable = false)
    private Integer tipoPrivacidade = 1;

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publicacao> publicacoes = new ArrayList<>();

    @OneToMany(mappedBy = "seguidor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Segue> seguindo = new ArrayList<>();

    @OneToMany(mappedBy = "seguido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Segue> seguidores = new ArrayList<>();

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversa> conversas = new ArrayList<>();

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participa> participa = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public Integer getTipoPrivacidade() {
        return tipoPrivacidade;
    }
    public void setTipoPrivacidade(Integer tipoPrivacidade) {
        this.tipoPrivacidade = tipoPrivacidade;
    }
    public List<Publicacao> getPublicacoes() {return publicacoes;}
    public void setPublicacoes(List<Publicacao> publicacoes) { this.publicacoes = publicacoes; }
    public List<Segue> getSeguidores() {return seguidores;}
    public void setSeguidores(List<Segue> seguidores) { this.seguidores = seguidores; }
    public List<Segue> getSeguindo() { return seguindo; }
    public void setSeguindo(List<Segue> seguindo) { this.seguindo = seguindo; }
}
