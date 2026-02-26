package br.com.redesocial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "segue")
public class Segue {
    @EmbeddedId
    private SegueId id;

    @ManyToOne
    @JsonIgnore
    @MapsId("idPerfilSeguidor")
    @JoinColumn(name = "user_seguidor", nullable = false)
    private Perfil seguidor;

    @ManyToOne
    @JsonIgnore
    @MapsId("idPerfilSeguido")
    @JoinColumn(name = "user_seguido", nullable = false)
    private Perfil seguido;

    public Segue() {
    }

    public Segue(Perfil seguidor, Perfil seguido) {
        this.seguidor = seguidor;
        this.seguido = seguido;
        this.id = new SegueId(seguidor.getId(), seguido.getId());
    }

    public SegueId getId() {return id;}
    public void setId(SegueId id) {this.id = id;}
    public Perfil getSeguidor() {return seguidor;}
    public void setSeguidor(Perfil seguidor) {this.seguidor = seguidor;}
    public Perfil getSeguido() {return seguido;}
    public void setSeguido(Perfil seguido) {this.seguido = seguido;}
}
