package br.com.redesocial.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversa")
public class Conversa {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conversa")
    private Long idConversa;

    @ManyToOne
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @Column(name = "tipo_conversa", nullable = false)
    private String tipoConversa = "privada";

    @OneToMany(mappedBy = "conversa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participa> participantes = new ArrayList<>();

    public Conversa() {}

    public Conversa(Perfil perfil) {
        this.perfil = perfil;
        this.tipoConversa = "privada";
    }

    public Long getIdConversa() {return idConversa;}

    public void setIdConversa(Long idConversa) {this.idConversa = idConversa;}
    public Perfil getPerfil() {return perfil;}
    public void setPerfil(Perfil perfil) {this.perfil = perfil;}
    public String getTipoConversa() {return tipoConversa;}
    public void setTipoConversa(String tipoConversa) {this.tipoConversa = tipoConversa;}
    public List<Participa> getParticipantes() {return participantes;}
    public void setParticipantes(List<Participa> participantes) {this.participantes = participantes;}

}
