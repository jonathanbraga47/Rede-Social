package br.com.redesocial.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversa")
public class Conversa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conversa")
    private Long idConversa;

    @OneToMany(mappedBy = "conversa", orphanRemoval = true)
    private List<Participa> participantes = new ArrayList<>();

    @OneToMany(mappedBy = "conversa", orphanRemoval = true)
    private List<Mensagem> mensagens = new ArrayList<>();


    public Conversa() {}

    public Long getIdConversa() {return idConversa;}

    public void setIdConversa(Long idConversa) {this.idConversa = idConversa;}
    public List<Participa> getParticipantes() {return participantes;}
    public void setParticipantes(List<Participa> participantes) {this.participantes = participantes;}
    public List<Mensagem> getMensagems() {return mensagens;}
    public void setMensagems(List<Mensagem> mensagems) {}

}