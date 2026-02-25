package br.com.redesocial.model;

import jakarta.persistence.*;

@Entity
@Table(name = "participa")
public class Participa {
    @EmbeddedId
    private ParticipaId id;

    @ManyToOne
    @MapsId("idPerfil")
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @ManyToOne
    @MapsId("idConversa")
    @JoinColumn(name = "id_conversa", nullable = false)
    private Conversa conversa;

    public Participa() {}

    public Participa(Perfil perfil, Conversa conversa) {
        this.id = new ParticipaId(perfil.getId(), conversa.getIdConversa());
        this.perfil = perfil;
        this.conversa = conversa;
    }

    public ParticipaId getId() {return id;}
    public void setId(ParticipaId id) {this.id = id;}
    public Perfil getPerfil() {return perfil;}
    public void setPerfil(Perfil perfil) {this.perfil = perfil;}
    public Conversa getConversa() {return conversa;}
    public void setConversa(Conversa conversa) {this.conversa = conversa;}

}
