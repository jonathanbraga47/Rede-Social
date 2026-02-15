package br.com.redesocial.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "publicacao")
public class Publicacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Perfil perfil;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String legenda;







    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
}
