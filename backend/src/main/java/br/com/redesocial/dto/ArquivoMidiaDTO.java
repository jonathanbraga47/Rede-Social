package br.com.redesocial.dto;

public class ArquivoMidiaDTO {
    private Long idArquivo;
    private Long idPublicacao;
    private String urlMidia;

    public ArquivoMidiaDTO() {}

    public ArquivoMidiaDTO(Long idPublicacao, String tipoMidia, String urlMidia) {
        this.idPublicacao = idPublicacao;
        this.urlMidia = urlMidia;
    }


    public String getUrlMidia() {return urlMidia;}
    public void setUrlMidia(String urlMidia) {this.urlMidia = urlMidia;}
    public Long getIdArquivo() {return idArquivo;}
    public Long getIdPublicacao() {return idPublicacao;}
    public void setIdArquivo(Long idArquivo) { this.idArquivo = idArquivo;}
    public void setIdPublicacao(Long idPublicacao) { this.idPublicacao = idPublicacao;}
    }
