package br.com.redesocial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "interacao")
@Inheritance(strategy = InheritanceType.JOINED)
// --- ADICIONE ESTAS 3 ANOTAÇÕES ABAIXO ---
@com.fasterxml.jackson.annotation.JsonTypeInfo(
        use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME,
        include = com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY,
        property = "type")
@com.fasterxml.jackson.annotation.JsonSubTypes({
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = InteracaoComentario.class, name = "comentario"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = InteracaoCurtida.class, name = "curtida")
})

public abstract class Interacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_interacao", nullable = false)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"interacoes", "publicacoes", "seguidores", "seguindo", "senha", "email", "nome"})
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @ManyToOne
    @JsonIgnoreProperties({"interacoes", "arquivos", "dataHora", "legenda"})
    @JoinColumn(name = "id_publicacao", nullable =  false)
    private Publicacao publicacao;

    @Column(name = "data_hora",nullable = false)
    private LocalDateTime dataHora;







    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Perfil getPerfil() {return perfil;}
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    public Publicacao getPublicacao() {return publicacao;}
    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }
    public LocalDateTime getDataHora() {return dataHora;}
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
