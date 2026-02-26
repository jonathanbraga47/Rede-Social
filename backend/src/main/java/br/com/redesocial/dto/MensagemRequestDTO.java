package br.com.redesocial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MensagemRequestDTO {
    @NotNull(message = "ID da conversa é obrigatório")
    private Long idConversa;

    @NotNull(message = "ID do perfil é obrigatório")
    private Long idPerfil;

    @NotBlank(message = "Conteúdo é obrigatório")
    private String conteudo;

    public MensagemRequestDTO() {}

    public Long getIdConversa() { return idConversa; }
    public void setIdConversa(Long idConversa) { this.idConversa = idConversa; }
    public Long getIdPerfil() { return idPerfil; }
    public void setIdPerfil(Long idPerfil) { this.idPerfil = idPerfil; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
}
