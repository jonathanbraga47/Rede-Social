package br.com.redesocial.model;

import jakarta.persistence.*;

@Entity
@Table(name = "arquivo_midia")
public class ArquivoMidia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_arquivo")
    private Long idArquivo;

    @ManyToOne
    @JoinColumn(name = "id_publicacao", nullable = false)
    private Publicacao publicacao;

    @Column(name = "tipo_midia", nullable = false)
    private String tipoMidia;

    public ArquivoMidia() {}

    public ArquivoMidia(Publicacao publicacao, String tipoMidia) {
        this.publicacao = publicacao;
        this.tipoMidia = tipoMidia;
    }

    public Long getIdArquivo() {return idArquivo;}
    public void setIdArquivo(Long idArquivo) {this.idArquivo = idArquivo;}
    public Publicacao getPublicacao() {return publicacao;}
    public void setPublicacao(Publicacao publicacao) {this.publicacao = publicacao;}
    public String getTipoMidia() {return tipoMidia;}
    public void setTipoMidia(String tipoMidia) {this.tipoMidia = tipoMidia;}
}
