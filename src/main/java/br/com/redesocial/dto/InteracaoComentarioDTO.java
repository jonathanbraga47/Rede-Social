package br.com.redesocial.dto;

public class InteracaoComentarioDTO {
    private Long usuarioId;
    private Long publicacaoId;
    private String conteudo;

    public InteracaoComentarioDTO() {}

    public InteracaoComentarioDTO(Long usuarioId, Long publicacaoId, String conteudo){
        this.usuarioId = usuarioId;
        this.publicacaoId = publicacaoId;
        this.conteudo = conteudo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPublicacaoId() {
        return publicacaoId;
    }

    public void setPublicacaoId(Long publicacaoId) {
        this.publicacaoId = publicacaoId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
