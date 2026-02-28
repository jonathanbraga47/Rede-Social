package br.com.redesocial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "arquivo_midia")
public class ArquivoMidia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_arquivo")
    private Long idArquivo;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_publicacao", nullable = false)
    private Publicacao publicacao;

    @Column(name = "url_midia", nullable = false)
    private String urlMidia;

    public ArquivoMidia() {}

    public ArquivoMidia(Publicacao publicacao, String urlMidia) {
        this.publicacao = publicacao;
        this.urlMidia = urlMidia;
    }



    public Long getIdArquivo() {return idArquivo;}
    public void setIdArquivo(Long idArquivo) {this.idArquivo = idArquivo;}
    @JsonIgnore
    public Publicacao getPublicacao() {return publicacao;}
    public void setPublicacao(Publicacao publicacao) {this.publicacao = publicacao;}
    public String getUrlMidia() {return urlMidia;}
    public void setUrlMidia(String urlMidia) {this.urlMidia = urlMidia;}
}