package br.com.redesocial.dto;

public class InteracaoComentarioDTO {
    private Long usuarioId;      // ID do Perfil que comenta
    private Long publicacaoId;   // ID da Publicacao comentada
    private String conteudo;     // O texto do comentário






    // Getters e Setters
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getPublicacaoId() { return publicacaoId; }
    public void setPublicacaoId(Long publicacaoId) { this.publicacaoId = publicacaoId; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
}