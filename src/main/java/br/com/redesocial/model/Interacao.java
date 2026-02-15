package br.com.redesocial.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interacao")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Interacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInteracao;

    @ManyToOne
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "id_publicacao", nullable =  false)
    private Publicacao publicacao;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    public Interacao() {

    }

    public Long getId() {
        return idInteracao;
    }

    public void setId(Long id) {
        this.idInteracao = idInteracao;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

}
