package br.com.redesocial.dto;

public class ArquivoMidiaDTO {
    private Long idArquivo;
    private Long idPublicacao;
    private String tipoMidia;

    public ArquivoMidiaDTO() {}

    public ArquivoMidiaDTO(Long idPublicacao, String tipoMidia) {
        this.idPublicacao = idPublicacao;
        this.tipoMidia = tipoMidia;
    }

    public Long getIdArquivo() {return idArquivo;}
    public Long getIdPublicacao() {return idPublicacao;}
    public String getTipoMidia() {return tipoMidia;}
    public void setIdArquivo(Long idArquivo) { this.idArquivo = idArquivo;}
    public void setIdPublicacao(Long idPublicacao) { this.idPublicacao = idPublicacao;}
    public void setTipoMidia(String tipoMidia) {this.tipoMidia = tipoMidia;}

    }
